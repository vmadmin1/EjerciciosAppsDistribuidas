from tkinter import *
from zeep import Client


class CalculadoraGUI:
    def __init__(self, root):
        self.operador = ''
        self.cliente = None

        # CREAR CLIENTE
        self.crear_cliente()

        # Configurar ventana principal
        root.geometry('400x200')
        root.resizable(0, 0)
        root.title("CALCULADORA")
        root.config(bg='white')

        # Panel de la calculadora
        self.panel_calculadora = Frame(root, bd=1, relief=FLAT, background='white')
        self.panel_calculadora.pack()

        # Visor de la calculadora
        self.visor_calculadora = Entry(self.panel_calculadora, font=('Dosis', 16, 'bold'), width=32, bd=1)
        self.visor_calculadora.grid(row=0, column=0, columnspan=5)

        # Botones de la calculadora
        botones_calculadora = ['7', '8', '9', '+', '4', '5', '6', '-', '1', '2', '3', '*', '=', 'C', '0', '/']
        self.botones_guardados = []

        fila = 1
        columna = 0

        for texto_boton in botones_calculadora:
            boton = Button(self.panel_calculadora, text=texto_boton, font=('Dosis', 16, 'bold'), fg='black',
                           bg='azure4', bd=1, width=5, command=lambda texto=texto_boton: self.click_boton(texto))
            self.botones_guardados.append(boton)
            boton.grid(row=fila, column=columna)
            columna += 1

            if columna == 4:
                fila += 1
                columna = 0

    def crear_cliente(self):
        # CREAR CLIENTE ZEEP
        self.cliente = Client("http://192.168.227.67:8080/ServerOperacionesV3/CalculadoraWSP3Service?wsdl")

    def click_boton(self, operacion):
        if operacion in ['+', '-', '*', '/']:
            self.operador += operacion
            self.visor_calculadora.delete(0, END)
            self.visor_calculadora.insert(END, self.operador)
        elif operacion == '=':
            self.obtener_resultado()
        elif operacion == 'C':
            self.borrar()
        else:
            self.operador += operacion
            self.visor_calculadora.delete(0, END)
            self.visor_calculadora.insert(END, self.operador)

    def borrar(self):
        self.operador = ''
        self.visor_calculadora.delete(0, END)

    def obtener_resultado(self):
        try:
            if not isinstance(self.operador, str):
                raise ValueError("La operación debe ser una cadena")

            print("Operador:", self.operador)

            # Buscar el operador dentro de la cadena
            for i, char in enumerate(self.operador):
                if char in ['+', '-', '*', '/']:
                    operador = char
                    operando1 = int(self.operador[:i])
                    operando2 = int(self.operador[i + 1:])
                    break
            else:
                raise ValueError("No se encontró ningún operador en la operación")

            if operador == '+':
                resultado = self.cliente.service.sumar(operando1, operando2)
            elif operador == '-':
                resultado = self.cliente.service.restar(operando1, operando2)
            elif operador == '*':
                resultado = self.cliente.service.multiplicar(operando1, operando2)
            elif operador == '/':
                resultado = self.cliente.service.dividir(operando1, operando2)

            self.visor_calculadora.delete(0, END)
            self.visor_calculadora.insert(0, resultado)
            self.operador = ''
        except Exception as e:
            print("Error:", e)
            self.visor_calculadora.delete(0, END)
            self.visor_calculadora.insert(0, "Error")


if __name__ == "__main__":
    root = Tk()
    calculadora = CalculadoraGUI(root)
    root.mainloop()