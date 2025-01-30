document.addEventListener("DOMContentLoaded", async () => {
    const movieDetail = document.getElementById("movie-detail");
    const url = new URLSearchParams(window.location.search);
    const movieId = url.get('movieId');
    const authToken = localStorage.getItem('authToken');

    function convertMinutesToTimeFormat(totalMinutes) {
        const hours = Math.floor(totalMinutes / 60);
        const minutes = totalMinutes % 60;
        return `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:00`;
    }

    function convertToString(timeString) {
        const [hours, minutes] = timeString.split(':').map(Number);
        return (hours * 60) + minutes;
    }

    function convertTo12HourFormat(timeStr) {
        const [hours, minutes] = timeStr.split(':');
        let hour = parseInt(hours);
        const ampm = hour >= 12 ? 'PM' : 'AM';
        hour = hour % 12;
        if (hour === 0) hour = 12;
        return `${hour}:${minutes} ${ampm}`;
    }

    function convertToDurationFormat(timeStr) {
        const [hours, minutes] = timeStr.split(':');
        return `${hours}:${minutes} hr`;
    }

    function convertToHumanReadable(dateString) {
        const date = new Date(dateString);
        const months = ["January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"];
        return `${months[date.getMonth()]} ${date.getDate()}, ${date.getFullYear()}`;
    }

    async function fetchMovieDetail() {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/movie/detail/cinema/${movieId}`, {
                method: "GET",
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                }
            });
            if (!response.ok) {
                if (response.status === 401) {
                    alert("Unauthorized access. Redirecting to login.");
                    window.location.href = '../loginPage/login.html';
                } else {
                    const error = await response.json();
                    alert(error.message);
                }
            }
            return await response.json();
        } catch (error) {
            console.error('Error fetching movie:', error);
            alert("Error while fetching movie details");
        }
    }

    let movie = await fetchMovieDetail();
    console.log(movie);

    function renderMovieDetails() {
        document.getElementById('body').style.backgroundImage =
            `url("http://localhost:8080/movieImage/${movie.imagePath || ''}")`;
        const movieDetailHTML = `
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
                                <p>${convertTo12HourFormat(movie.viewTime) || 'Show time not available'}</p>
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
                                <p>${movie.price || 'Price not available'} Birr</p>
                            </div>
                        </div>
                    </div>

                    <div class="action-buttons">
                        <button class="btn delete-btn">Delete</button>
                        <button class="btn edit-btn">Edit</button>
                    </div>                 
                </div>
            </div>
        `;

        movieDetail.innerHTML = movieDetailHTML;
        addEventListeners();
    }

    function addEventListeners() {
        document.querySelector(".edit-btn").addEventListener("click", () => {
            document.getElementById('movie-title').value = movie.title;
            document.getElementById('movie-description').value = movie.description;
            document.getElementById('movie-view-time').value = movie.viewTime;
            document.getElementById('movie-duration').value = convertToString(movie.duration);
            document.getElementById('movie-view-date').value = movie.viewDate;
            document.getElementById('movie-total-seats').value = movie.seats;
            document.getElementById('movie-price').value = movie.price;
            document.getElementById('movie-genres').value = movie.genres;
            document.getElementById("add-modal").style.display = "flex";
        });

        const deleteBtn = document.querySelector('.delete-btn');
        const deleteModal = document.getElementById('delete-modal');
        const cancelBtn = document.getElementById('cancel-btn');
        const confirmBtn = document.getElementById('confirm-btn');

// Show modal when delete button is clicked
        deleteBtn.addEventListener('click', () => {
            deleteModal.style.display = 'flex'; // Display the modal
        });

// Hide modal when cancel button is clicked
        cancelBtn.addEventListener('click', () => {
            deleteModal.style.display = 'none'; // Hide the modal
        });

// Handle confirm button click
        confirmBtn.addEventListener('click', async () => {
            // alert('Item deleted!'); // Replace with your delete logic
            deleteModal.style.display = 'none'; // Hide the modal
            await deleteMovie();
            window.location.href = "../cinemaHomePage/cinemaHome.html";
        });

// Hide modal when clicking outside the modal
        window.addEventListener('click', (event) => {
            if (event.target === deleteModal) {
                deleteModal.style.display = 'none'; // Hide the modal
            }
        });

        const editButton = document.querySelector(".edit-btn");
        const modal = document.getElementById("add-modal");
        const closeButton = document.getElementById("close-button");
        const updateButton = document.getElementById("add-movie-form");
        closeButton.addEventListener("click", () => {
            modal.style.display = "none";
        });

        window.addEventListener("click", (event) => {
            if (event.target === modal) {
                modal.style.display = "none";
            }
        });

    }



    async function deleteMovie() {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/movie/${movieId}`, {
                method: "DELETE",
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    // 'Content-Type': 'application/json'
                },
            });

            if (!response.ok) {
                const errorData = await response.text();
                throw new Error(`Error: ${response.status} - ${errorData.message}`);
            }

            // alert(result)
            // console.log("Movie deleted successfully:", result);
            // alert("Movie deleted successfully!");

            // Optionally, you can return the result or perform additional actions
            return await response.text();

        } catch (error) {
            console.error("Error deleting movie:", error.message);
            alert("Error while deleting movie: " + error.message);
        }
    }

    async function updateMovie(formData) {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/movie/${movieId}`, {
                method: "PUT",
                body: formData,
                headers: { 'Authorization': `Bearer ${authToken}` },
            });

            if (!response.ok) {
                const errorData = await response.json();

                if (typeof errorData === 'object' && errorData !== null) {
                    const errorMessages = Object.values(errorData).map(message =>
                        message.replace("View date", "Show Date")
                    ).join('\n');

                    alert(errorMessages);
                } else {
                    alert("An unexpected error occurred.");
                }
            }
            movie = await response.json();
            movie = await fetchMovieDetail();
            alert("Movie updated successfully!");
            document.getElementById("add-modal").style.display = "none";
            renderMovieDetails();
        } catch (error) {
            console.error("Error updating movie:", error);
            alert("Error while updating movie");
        }
    }

    document.getElementById("add-movie-form").addEventListener("submit", async (event) => {
        event.preventDefault();
        const minutesDuration = parseInt(document.getElementById('movie-duration').value, 10);

        const formatViewTime =convertMinutesToTimeFormat(minutesDuration)



        const timeInput = document.getElementById('movie-view-time').value; // Example input
        const [time, modifier] = timeInput.split(" "); // Split into time and AM/PM
        let [hours, minutes] = time.split(":").map(Number); // Split into hours and minutes

// Convert to 24-hour format
        if (modifier === "PM" && hours !== 12) {
            hours += 12;
        }
        if (modifier === "AM" && hours === 12) {
            hours = 0;
        }

// Format to HH:mm:ss
        const localTime = `${String(hours).padStart(2, "0")}:${String(minutes).padStart(2, "0")}:00`;


        const imageFile = document.querySelector('input[type="file"]').files[0];
        const title = document.getElementById('movie-title').value;
        const description = document.getElementById('movie-description').value;
        const viewTime = localTime;
        const duration = formatViewTime;
        const viewDate = document.getElementById('movie-view-date').value;
        const totalSeats = document.getElementById('movie-total-seats').value;
        const pricePerSeat = document.getElementById('movie-price').value;
        const genres = document.getElementById('movie-genres').value.split(',');

        const createMovieDto =
            {
                title:title,
                description:description,
                genres:genres,
                viewTime:viewTime,
                viewDate:viewDate,
                duration:duration,
                price:pricePerSeat,
                seats:totalSeats
            };
        console.log(createMovieDto,imageFile)
        const formData = new FormData();
        formData.append("updateMovieDto", new Blob([JSON.stringify(createMovieDto)], { type: "application/json" }));
        formData.append("image", imageFile);

        await updateMovie(formData);

        // modal.style.display = "none";
    });

    renderMovieDetails();
});
