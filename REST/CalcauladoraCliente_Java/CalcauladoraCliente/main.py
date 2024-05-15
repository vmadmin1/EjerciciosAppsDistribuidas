import tkinter as tk
from tkinter import ttk
import requests

class CalculadoraCliente:
    def __init__(self, root):
        self.root = root
        self.root.title("Calculadora")
        self.root.geometry("360x442") 
        self.root.configure(background="#333333")

        self.operacion_lista = []
        self.url_servidor = 'http://192.168.215.39:3000/operacion'

        self.frame_principal = ttk.Frame(self.root, padding=(10, 10, 10, 10))
        self.frame_principal.pack(expand=True, fill="both")

        self.label_resultado = tk.Label(self.frame_principal, text="", font=('Helvetica', 30), anchor="e", width=10, background="white")
        self.label_resultado.grid(row=0, column=0, columnspan=4, padx=10, pady=10, sticky="ew")

        botones = [
            ('C', 1, 0), ('/', 1, 1), ('*', 1, 2), ('B', 1, 3),
            ('7', 2, 0), ('8', 2, 1), ('9', 2, 2), ('-', 2, 3),
            ('4', 3, 0), ('5', 3, 1), ('6', 3, 2), ('+', 3, 3),
            ('1', 4, 0), ('2', 4, 1), ('3', 4, 2),
            ('0', 5, 0, 2), ('.', 5, 2), ('=', 4, 3, 2)
        ]

        for (texto, fila, columna, *span) in botones:
            if texto == 'B':  # Botón de borrar un carácter
                boton = tk.Button(self.frame_principal, text=texto, command=self.borrar_caracter, width=6, height=2, background="#ffd699", font=('Helvetica', 14))
            elif texto in ['+', '-', '*', '/']:  # Botones de operaciones
                boton = tk.Button(self.frame_principal, text=texto, command=lambda t=texto: self.agregar_caracter(t), width=6, height=2, background="#ffd699", font=('Helvetica', 14))
            elif texto == '=':  # Botón de igual
                boton = tk.Button(self.frame_principal, text=texto, command=self.enviar_operacion, width=6, height=2, background="#ffd699", font=('Helvetica', 14))
                boton.grid(row=fila, column=columna, padx=5, pady=5, sticky="nsew", columnspan=span[0] if span else 1, rowspan=span[2] if len(span) > 2 else 2)
            else:  # Otros botones (números y punto)
                boton = tk.Button(self.frame_principal, text=texto, command=lambda t=texto: self.agregar_caracter(t), width=6, height=2, background="#99ccff", font=('Helvetica', 14))
            boton.grid(row=fila, column=columna, padx=5, pady=5, sticky="nsew", columnspan=span[0] if span else 1)  # Ajuste del relleno

    def agregar_caracter(self, caracter):
        if caracter == 'C':
            self.operacion_lista = []  # Limpiar la lista de operaciones
        else:
            self.operacion_lista.append(caracter)
        contenido_actual = ''.join(self.operacion_lista)
        self.label_resultado["text"] = contenido_actual

    def borrar_caracter(self):
        if self.operacion_lista:
            self.operacion_lista.pop()
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