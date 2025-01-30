document.addEventListener("DOMContentLoaded",async ()=>{
    const movieDetail = document.getElementById("movie-detail");
    const url = new URLSearchParams(window.location.search);
    const movieId = url.get('movieId')
    const authToken = localStorage.getItem('authToken');

    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        link.classList.remove('active');
    });

    function convertTo12HourFormat(timeStr) {
        const [hours, minutes, seconds] = timeStr.split(':'); // Split the string into hours, minutes, and seconds
        let hour = parseInt(hours);
        const ampm = hour >= 12 ? 'PM' : 'AM'; // Determine AM or PM
        hour = hour % 12; // Convert to 12-hour format
        if (hour === 0) hour = 12; // Handle midnight (00:00:00 becomes 12:00 AM)

        return `${hour}:${minutes} ${ampm}`;
    }

    function convertToDurationFormat(timeStr) {
        const [hours, minutes] = timeStr.split(':'); // Split the string into hours and minutes
        return `${hours}:${minutes} hr`; // Return in the format "hh:mm hr"
    }

    function convertToHumanReadable(dateString) {
        const date = new Date(dateString);
        const months = ["January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"];
        return `${months[date.getMonth()]} ${date.getDate()}, ${date.getFullYear()}`;
    }

    async function fetchMovieDetail(){
        try{
            const response = await fetch(`http://localhost:8080/api/v1/movie/${movieId}`,{
                method:"GET",
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                }
            })
            if(!response.ok){
                const error = await response.json();
                if (response.status === 401) {
                    alert("Unauthorized access. Redirecting to login.");
                    window.location.href = '../loginPage/login.html';
                } else {
                    alert(error.message);

                }

            }
            return await response.json();

        }catch (error){
            console.error('Error fetching movies:', error);
            alert("Error while fetching movie detail")
        }
    }
    let movie = await fetchMovieDetail();
    console.log(movie)
    const movieDetailHTML = `
            <div class="movie-detail" id="movie-detail">
                <div class="movie-content">
                    <div class="movie-poster">
                        <img src="http://localhost:8080/movieImage/${movie.imagePath || "../loginPage/2827349.jpg"}" alt="Movie Poster" class="poster-image">
                        <div class="movie-description">
                            <p>${movie.description || 'No description available'}</p>
                        </div>
                    </div>

                    <div class="movie-info">
                        <div class="info-card">
                            <div class="info-item">
                                <h2>Title</h2>
                                <p>${movie.title || 'Title not available'}</p>
                            </div>
                            <div class="info-item">
                                <h2>Genre</h2>
                                <p>${movie.genres || 'Genre not available'}</p>
                            </div>
                            <div class="time-info">
                                <div class="info-item">
                                    <h2>Show Time</h2>
                                    <p>${ convertTo12HourFormat(movie.viewTime)|| 'Show time not available'}</p>
                                </div>
                                <div class="info-item">
                                    <h2>Show Date</h2>
                                    <p>${convertToHumanReadable(movie.viewDate) || 'Show date not available'}</p>
                                </div>
                                <div class="info-item">
                                    <h2>Duration</h2>
                                    <p>${convertToDurationFormat(movie.duration) || 'Duration not available'}</p>
                                </div>
                            </div>
                            <div class="seat-price">
                            <div class="info-item seats">
                                <div>
                                    <h2>Total Seats</h2>
                                    <p>${movie.seats || 'Total seats not available'}</p>
                                </div>
                                <div>
                                    <h2>Available Seats</h2>
                                    <p id="available-seats">${movie.seats - movie.bookedSeats || 'Available seats not available'}</p>
                                </div>
                            </div>
                            <div class="info-item">
                                <h2>Price per Seat</h2>
                                <p>${movie.price || 'price pre seat not available'} Birr</p>
                            </div>
                            </div>
                        </div>

                        <div class="action-buttons">
                            <button class="btn watchlist-btn" >${movie.alreadyInTheWatchList ? "Already in watchList" :'Add to Watch List'}</button>
                             <button class="btn reserve-btn" ${movie.alreadyBooked ? 'disabled' : ''}>
                            ${movie.alreadyBooked ? 'Reserved' : 'Reserve'}
                        </button>
                        </div>
                        <div class="detail-button">
                            <button class="btn detail-btn">Cinema Detail</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

    document.getElementById('body').style.backgroundImage = `url("http://localhost:8080/movieImage/${movie.imagePath}")`;
    movieDetail.innerHTML =movieDetailHTML;


    // Get the modal and buttons
    const modal = document.getElementById('reservationModal');
    const reserveButton = document.querySelector('.reserve-btn');
    const cancelButton = document.querySelector('.cancel-btn');
    const maxSeats = document.getElementById('seats-info')
    const seatInput = document.getElementById('seats')
    const errorSpan = document.getElementById('error')
    const reservationForm = document.getElementById('reservationForm');
    const watchListButton = document.querySelector('.watchlist-btn')
    const detailBtn = document.querySelector('.detail-btn')


    const initialSeats =  movie.seats - movie.bookedSeats;

    let availableSeats = movie.seats - movie.bookedSeats;

    maxSeats.textContent = `Maximum available ${availableSeats} seats`;


    reserveButton.addEventListener('click', () => {
        modal.classList.add('active');
    });


    cancelButton.addEventListener('click', () => {
        modal.classList.remove('active');
        seatInput.value =0;
        maxSeats.textContent = `Maximum available ${initialSeats} seats`;
    });


    modal.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.classList.remove('active');
            seatInput.value =0;
            maxSeats.textContent = `Maximum available ${initialSeats} seats`;
        }
    });




// Update the available seats dynamically based on user input
    seatInput.addEventListener('input', () => {
        let enteredSeats = parseInt(seatInput.value, 10);

        if (isNaN(enteredSeats) || enteredSeats <= 0) {
            errorSpan.textContent = 'Please enter a valid number of seats.';
            errorSpan.style.display = 'block';
            maxSeats.textContent = `Maximum available ${initialSeats} seats`;
            availableSeats=initialSeats;
            return;
        }
        if (enteredSeats > availableSeats) {

            errorSpan.textContent = 'Number exceeds available seats!';
            errorSpan.style.display = 'block';
            maxSeats.textContent = `Maximum available ${initialSeats} seats`;
        } else {

            errorSpan.style.display = 'none';
            availableSeats -= enteredSeats;
            maxSeats.textContent = `Maximum available ${availableSeats} seats`;
            availableSeats += enteredSeats

        }
    });

    reservationForm.addEventListener('submit',async (e) => {
        e.preventDefault();
        let enteredSeats = parseInt(seatInput.value,10);
        if (isNaN(enteredSeats) || enteredSeats <= 0 || enteredSeats > availableSeats) {
            errorSpan.textContent = 'Please enter a valid number of seats or number exceeds available seats!';
            errorSpan.style.display = 'block';
            return;
        }

        const data = {
            seats:enteredSeats
        }

        try{
            const response = await fetch(`http://localhost:8080/api/v1/booking/${movieId}`,{
                method:"POST",
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                },
                body:JSON.stringify(data)

            })
            if(!response.ok){
                const error = await response.json();
                if (response.status === 401) {
                    alert("Unauthorized access. Redirecting to login.");
                    window.location.href = '../loginPage/login.html';
                } else {
                    alert(error.message);
                }
            }
            modal.classList.remove('active');
            seatInput.value =0;
            maxSeats.textContent = `Maximum available ${availableSeats-enteredSeats} seats`;
            const movie = await fetchMovieDetail();
            const seats = document.getElementById('available-seats');
            seats.innerText = movie.seats - movie.bookedSeats;
            reserveButton.innerHTML = "Reserved"
            reserveButton.disabled = true;
            // renderMovieDetails(movie);

        }catch (error){
            console.error('Error while booking movies:', error);
            alert("Error while reserving a seat")
        }
    });

    watchListButton.addEventListener('click', async () => {


        const method = movie.alreadyInTheWatchList ? "DELETE" : "POST";

        const url = movie.alreadyInTheWatchList
            ? `http://localhost:8080/api/v1/watchlist/${movie.alreadyInTheWatchList}`
            : `http://localhost:8080/api/v1/watchlist/${movieId}`;


        try {
            const response = await fetch(url, {
                method: method,
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                const error = await response.json();
                if (response.status === 401) {
                    alert("Unauthorized access. Redirecting to login.");
                    window.location.href = '../loginPage/login.html';
                } else {
                    alert(error.message);
                }
            }

            // Re-fetch the movie details after adding/removing from the watchlist
            const updatedMovie = await fetchMovieDetail();
            watchListButton.innerText = updatedMovie.alreadyInTheWatchList
                ? "Already in Watchlist"
                : "Add to Watchlist";
            movie=updatedMovie

        } catch (error) {
            console.error('Error while adding/removing from watchlist:', error);
            alert("Error while adding/removing from watchlist");
        }
    });

    detailBtn.addEventListener('click',()=>{
        window.location.href = `../userCinemaDetailPage/userCinemaDetail.html?cinemaId=${movie.cinema.id}`;
    })


});