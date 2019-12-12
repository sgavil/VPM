# Extras
Cuando el jugador se encuentre en un punto sin solución, hacer que el móvil vibre y haga algún sonido. Para saber que no tiene solución se utiliza la definición de grafo conexo, donde si no lo es, no tiene solución.

# Input
## PC
Hay que usar _GetMouseButtonUp_ para controlar la liberación y ver si se ha completado el mapa entero (mirando si todos los bool están a cierto).

## Android
Hay que buscar por ID de los dedos porque cuando cambia el tamaño del array _touches_ no se reasignan los ID

# Escalado 
En el _Canvas Scaler_ es recomendable usar **Scale With Screen Size**. Para saber el tamaño de la pantalla usamos el objeto estático de Unity _Screen_.

## Ejemplo
Tenemos fijo el ancho del canvas a 800, y se colocan los objetos en el canvas teniendo en cuenta ese ancho. Esta información será fija y cuando se ponga, vamos a terminar diciendo que el canvas de arriba ocupa tanto, sabiendo que a lo ancho ocupa 800.

Nuestro móvil no tiene 800 de ancho, por lo que usamos _Screen.width_ y se echan cuentas.

## Ejemplo
En nuestro móvil, 800 de canvas son 1080 físicos, un alto de 150 en el canvas, ¿cuánto ocupa de verdad?

Se suman la altura de los canvas, se le resta la _Screen.height_ y se saben cuantos píxeles quedan en el centro. 
Hay que hacer regla de 3 con la cámara.

---

# Guardar el progreso
+ Número de monedas que se han conseguido viendo videos.
+ Número de niveles superados.
+ Tiempo entre retos.
+ Si ha comprado la opción de quitar los anuncios permanentemente.

## Serialización
+ **Binario**. Hay que elegir si se guarda en LittleEndian o en BigEndian. Ocupa menos espacio. Es menos amigable para el usuario ya que no se puede ver bien lo que pone.
+ **TXT**. Ocupa más espacio. Es más amigable para el usuario.
+ **JSON**. Unity contiene su propia clase para estas funciones.
+ **YAML**.
+ **XML**.

Siempre hay que decir cuál es la versión en serialización. Sería el primer campo del archivo. En particular, si vamos a usar la opción de _binario_ no podemos usar este truco.

## Persistencia
En android hay varias opciones:
+ **Preferencias de usuario**.
+ **Ficheros**. No podemos escribir en cualquier sitio usando ficheros. Esto va en memoria interna del móvil, esto significa que el usuario no podrá verlos.