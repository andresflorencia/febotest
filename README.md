# febotest
Aplicación de noticias en Android, proceso de selección FOMAT


Para la obtención de las noticias se utilizó la API de: https://newsapi.org
Esta API devuelve noticias por categorías como: Salud, Deporte, Tecnología, entre otras. También se puede filtrar por búsqueda de texto, 
por país y por idioma. 
En el caso de este proyecto se utilizó las categorías: 
* General, Deportes, Salud, Negocios, Entretenimiento, Ciencia y Tecnología.
En cuanto al idioma, se las filtró por: Español

----- CÓDIGO ------
El proyecto se desarrolló en Android Studio, utilizando como lenguaje: JAVA
Para la visualización de las noticias en la app, se hizo uso de:
* Una activity principal (MainActivity)
* 1 RecyclerView para el Listado de categorías con orientación horizontal (rvCategories)
* 1 RecyclerView para el Listado de noticias con orientación vertical (rvNews)
* 1 Adapter para las categorías
* 1 Adapter para las noticias
* CardView (para cada noticias)
* DialogFragment para mostrar una previsualización de la noticia como modal
* Clases Modelos: 
  1.- Category
  2.- Article (News)
  3.- ResponseNews (Estructura de como devuelve la respuesta la API)
  
