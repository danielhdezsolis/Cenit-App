Sistema de Monitoreo de Enfriadores
Este proyecto es una aplicación móvil desarrollada en Kotlin para dispositivos Android que permite el monitoreo en tiempo real de enfriadores industriales. La aplicación recopila datos de sensores de temperatura, humedad, voltaje, consumo eléctrico y otros dispositivos conectados mediante el protocolo Zigbee, los cuales son gestionados a través de Home Assistant en una Raspberry Pi. Los datos son almacenados y procesados en Supabase, una base de datos en tiempo real que permite generar reportes y certificados de las pruebas realizadas en los dispositivos monitoreados.

Características
Monitoreo en tiempo real de enfriadores conectados mediante Zigbee.
Configuración de pruebas de rendimiento a través de un proceso de onboarding.
Generación automática de certificados en formato PDF al finalizar cada prueba.
Notificaciones push para informar al usuario sobre el estado y resultados de las pruebas.
Visualización de gráficos detallados del comportamiento de los dispositivos durante la prueba.
Gestión de usuarios, autenticación y cierre de sesión.
Tecnologías utilizadas
Kotlin: Lenguaje de programación principal para el desarrollo de la aplicación.
Supabase: Plataforma de base de datos en tiempo real que maneja la autenticación y almacenamiento de datos.
Home Assistant: Plataforma de automatización del hogar utilizada para gestionar los sensores y dispositivos Zigbee.
Raspberry Pi: Dispositivo que ejecuta Home Assistant y gestiona la red Zigbee.
Firebase: Manejo de notificaciones push y autenticación de usuarios.
Zigbee: Protocolo de comunicación inalámbrica para la interconexión de sensores.
Requisitos del sistema
Android 8.0 (Oreo) o superior.
Raspberry Pi 4 con Home Assistant instalado para gestionar los sensores Zigbee.
Conexión a internet para la sincronización de datos con Supabase.

Funcionalidades principales
Inicio de sesión: Los usuarios pueden iniciar sesión utilizando Firebase Authentication.

Gestión de dispositivos: Listado de dispositivos disponibles, registrados previamente en Supabase.

Configuración de pruebas: El usuario configura pruebas a través del módulo de onboarding, seleccionando enfriadores y sensores.

Monitoreo en tiempo real: Los resultados de la prueba se actualizan en tiempo real y se presentan en gráficos.

Generación de certificados: Al finalizar una prueba, se genera un certificado en formato PDF que puede ser descargado por el usuario.

Notificaciones: Las notificaciones push informan al usuario del estado y resultado de las pruebas.