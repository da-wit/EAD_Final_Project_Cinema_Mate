document.addEventListener("DOMContentLoaded",()=>{
    const movieGrid = document.getElementById('movie-grid');
    const authToken = localStorage.getItem('authToken')
    const url = new URLSearchParams(window.location.search)
    const cinemaId = url.get('cinemaId');
    const fromCinema = cinemaId ? true : false;
    const searchInput = document.getElementById("search-input"); // Add search input field reference
    let currentSearch ="";

    if(fromCinema){
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        link.classList.remove('active');
    });
    }

    function convertToHumanReadable(dateString) {
        const date = new Date(dateString);
        const months = ["January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"];
        return `${months[date.getMonth()]} ${date.getDate()}, ${date.getFullYear()}`;
    }


    // const fetchUrl = fromCinema ? `http://localhost:8080/api/v1/movie/cinema/${cinemaId}` : `http://localhost:8080/api/v1/movie?serach=${currentSearch}`;


    async function fetchMovies(){
        const fetchUrl = fromCinema
            ? `http://localhost:8080/api/v1/movie/cinema/${cinemaId}?search=${currentSearch}`
            : `http://localhost:8080/api/v1/movie?search=${currentSearch}`;

        console.log(fetchUrl)
        try{
            const response = await fetch(fetchUrl,{
                method:"GET",
                headers: {
                    'Authorization': `Bearer ${authToken}`, // Add the token to the header
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {

                const error = await response.json();
                alert(error.message)
                window.location.href = '../loginPage/login.html';
                return
            }

            // console.log(d)
            return await response.json();

        }catch (e){
            console.error('Error fetching movies:', e);
            return [];
        }
    }


    // Function to create movie cards
    function createMovieCard(movie) {
        return `
            <div class="movie-card" id="${movie.id}">
                <img src="http://localhost:8080/movieImage/${movie.imagePath}" alt="${movie.title}" class="movie-image">
                <div class="movie-info">
                    <h3 class="movie-title">Title: ${movie.title}</h3>
                    <p class="movie-date">Show Date: ${convertToHumanReadable(movie.viewDate)}</p>
                </div>
            </div>
        `;
    }

    async  function generateMovieCard(){
        const movies = await fetchMovies();
        if(movies.length === 0){
            const message = fromCinema
                ? "This cinema currently has no movies listed. Please check back later!"
                : "No movies are currently available. Explore more options soon!";
            const text = `No movie found with name ${currentSearch}`
            movieGrid.innerHTML =`<p class="no-movie">${currentSearch ? text : message  }</p>`;
            return;
        }
        movieGrid.innerHTML = movies.map(movie => createMovieCard(movie)).join('');
        handleRedirect();
    }

    function handleRedirect(){
        const movieCards = document.querySelectorAll(".movie-card");
        movieCards.forEach((movie) => {
            movie.addEventListener('click',(e) => {
                const movieId = e.currentTarget.id;
                window.location.href = `../movieDetailPage/movieDetail.html?movieId=${movieId}`;
            })
        })
    }

    searchInput.addEventListener('input',(e)=>{
        currentSearch = e.target.value
        console.log(currentSearch)
        generateMovieCard()
    })

    generateMovieCard();



})