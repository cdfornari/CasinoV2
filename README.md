# Casino
## Guía de instalación

1) Descargar o clonar el proyecto del siguiente repositorio y guardarlo fuera del workspace de Eclipse.
   ##### https://github.com/cforna/CasinoV2 
#
2) En Eclipse, ir a File -> Import -> Gradle -> Existing Gradle Project, y seleccionar la carpeta principal del proyecto guardado fuera del workspace. Se importarán 3 carpetas en el Project Explorer.

3) Click derecho en la carpeta Casino-desktop -> Run as -> Run configurations.

4) En el lado derecho selecciona Java Application.

    ![Image text](https://libgdx.com/assets/images/dev/eclipse/3.png)
   
5) Click al icono para crear nueva configuracion arriba a la izquierda. 

    ![Image text](https://libgdx.com/assets/images/dev/eclipse/0.png)
    
6) Como clase principal, selecciona DesktopLauncher.

7) Click en la pestaña Arguments.

8) En Working Directory, selecciona Other y selecciona el directorio de la carpeta assets del projecto con la siguiente ruta: 
    ##### Casino/core/assets
    #
    ![Image text](https://libgdx.com/assets/images/dev/eclipse/1.png)
    
9) Guarda la configuracion y ya puedes correr el juego con el icono Run de Eclipse.