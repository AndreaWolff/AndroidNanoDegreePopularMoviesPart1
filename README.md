<h1>Android Nanodegree Popular Movies - Part 1</h1>

<b>Project Overview</b>
</br></br>
In this project, I built an app to allow users to discover the most popular movies playing. 
The development of this app is split into two parts, this project is about Part 1.
In this part I built the core experience of the movies app.
</br></br>
This app will:
<ul>
  <li>Display to the user with a grid layout of Popular Movie posters upon launch</li>
  <li>Allow the user to change the sort order via the Settings menu (which includes):
    <ul>
      <li>Popular Movies</li>
      <li>Top-Rated Movies</li>
    </ul>
   <li>Allow the user to select a movie poster and display the movie details screen with additional information such as:
    <ul>
      <li>original title</li>
      <li>movie poster image</li>
      <li>A plot synopsis</li>
      <li>user rating</li>
      <li>release date</li>
      <li>movie image backdrop</li>
    </ul>
   </li>
  </li>
</ul> 
<b>Why this Project?</b>
</br></br>
This project built onto the understanding of the foundational elements of programming in Android, emphasizing on the following:
<ul>
  <li>How to build a clean and compelling user interface (UI)</li>
  <li>How to fetch data from a network services</li>
  <li>How to optimize the expereience for various mobile devices</li>
</ul>
<b>How to run the Popular Movies app</b>
</br></br>
The Popular Movies app connects to theMovieDB server which displays the Popular/Top-Rated movie information. To be able to run this app, you will 
need to sign up for a free theMoviesDB api key at https://www.themoviedb.org/documentation/api. Once you have recieved this key, you will 
need to copy and paste your key into the MovieDao class (found in the project under 'features/common/repository' package) 
and replace /*Insert API KEY here*/ with your key.
</br></br>
<li>Example: @GET("movie/popular?api_key=/*Insert API_KEY here*/") Single<MoviesDto> getPopularMoviesList();</li>
</br>
By completing this, the Popular Movies app will be able to run and display the most Popular Movies!
</br></br>
<b>Below are screenshots from the app Popular Movies - Part 1:</b>
</br></br>
<img src="https://github.com/AndreaWolff/AndroidNanoDegreePopularMoviesPart1/blob/master/images/MainActivity-PopularMovies.png" height="500" width="300">
<img src="https://github.com/AndreaWolff/AndroidNanoDegreePopularMoviesPart1/blob/master/images/MainActivity-TopRatedMovies.png" height="500" width="300">
<img src="https://github.com/AndreaWolff/AndroidNanoDegreePopularMoviesPart1/blob/master/images/MainActivity-Settings.png" height="500" width="300">
<img src="https://github.com/AndreaWolff/AndroidNanoDegreePopularMoviesPart1/blob/master/images/DetailsActivity-MovieDetails.png" height="500" width="300">
<img src="https://github.com/AndreaWolff/AndroidNanoDegreePopularMoviesPart1/blob/master/images/MainActivity-PopularMovies-Landscape.png" height="300" width="500">
</br>
<b>What I learned after Part 1?</b>
<ul>
  <li>How to fetch data from the Internet with theMovieDB API</li>
  <li>How to use adapters and custom list layouts to populate a recycler view</li>
  <li>How to incorporate libraries to simplify the amount of code that needs to be written</li>
  <li>How to set up an advanced Android architecture and framework, including:</li>
    <ul>
      <li>Retrofit, GSON and RxJava2 libraries to help fetch data from the internet</li>
      <li>Dagger2 and Model View Presenter (MVP) to help with separation of concern</li>
      <li>Mockito to help test logic code</li>
    </ul>
</ul>
