import tkinter as tk
from tkinter import ttk
import requests


class CalculadoraCliente:
    def __init__(self, root):
        self.root = root
        self.root.title("Calculadora")
        self.root.geometry("300x450")

        self.operacion_lista = []
        self.url_servidor = 'http://192.168.215.39:3000/operacion'

        self.frame_principal = ttk.Frame(self.root)
        self.frame_principal.pack(padx=10, pady=10)

        self.label_resultado = ttk.Label(self.frame_principal, text="", font=('Helvetica', 16), anchor="e", width=15)
        self.label_resultado.grid(row=0, column=0, columnspan=4, padx=10, pady=10, sticky="ew")

        botones = [
            ('7', 1, 0), ('8', 1, 1), ('9', 1, 2), ('/', 1, 3),
            ('4', 2, 0), ('5', 2, 1), ('6', 2, 2), ('*', 2, 3),
            ('1', 3, 0), ('2', 3, 1), ('3', 3, 2), ('-', 3, 3),
            ('0', 4, 0), ('.', 4, 1), ('C', 4, 2), ('+', 4, 3),
            ('=', 5, 0)
        ]

        for (texto, fila, columna) in botones:
            if texto != '=':
                ttk.Button(self.frame_principal, text=texto, command=lambda t=texto: self.agregar_caracter(t),
                           width=5).grid(row=fila, column=columna, padx=5, pady=5)
            else:
                ttk.Button(self.frame_principal, text=texto, command=self.enviar_operacion, width=5).grid(row=fila,
                                                                                                          column=columna,
                                                                                                          padx=5,
                                                                                                          pady=5,
                                                                                                          columnspan=2)

    def agregar_caracter(self, caracter):
        self.operacion_lista.append(caracter)
        contenido_actual = ''.join(self.operacion_lista)
        self.label_resultado["text"] = contenido_actual

    def enviar_operacion(self):
        operacion = ''.join(self.operacion_lista)
        if operacion:
            try:
                # Enviar la operación al servidor
                response = requests.post(self.url_servidor, json={'operacion': 'custom', 'operando1': operacion})
                if response.status_code == 200:
                    resultado = response.json()['resultado']
                    self.label_resultado["text"] = resultado
                else:
                    self.label_resultado["text"] = "Error en el servidor"
            except Exception as e:
                self.label_resultado["text"] = "Error de conexión"
        else:
            self.label_resultado["text"] = "Error"


root = tk.Tk()
app = CalculadoraCliente(root)
root.mainloop()
