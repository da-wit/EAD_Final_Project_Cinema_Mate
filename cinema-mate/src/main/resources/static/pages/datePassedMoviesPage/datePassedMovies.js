document.addEventListener("DOMContentLoaded",async ()=>{

    const authToken = localStorage.getItem('authToken')
    let datePassedGrid = document.querySelector('.date-passed-grid');
    let currentSelectedMovieId;
    const searchInput = document.getElementById("search-input"); // Add search input field reference
    let currentSearch ="";

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
        // Parse the input date string
        const date = new Date(dateString);

        // Array of month names
        const months = [
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        ];

        // Extract year, month, and day
        const year = date.getFullYear();
        const month = months[date.getMonth()]; // Month is zero-based
        const day = date.getDate();

        // Return the human-readable format
        return `${month} ${day}, ${year}`;
    }


    function convertTo12HourFormat(timeStr) {
        const [hours, minutes, seconds] = timeStr.split(':'); // Split the string into hours, minutes, and seconds
        let hour = parseInt(hours);
        const ampm = hour >= 12 ? 'PM' : 'AM'; // Determine AM or PM
        hour = hour % 12; // Convert to 12-hour format
        if (hour === 0) hour = 12; // Handle midnight (00:00:00 becomes 12:00 AM)
        return `${hour}:${minutes} ${ampm}`;
    }

    async function getDatePassedMovies(){
        try {
            const response = await fetch(`http://localhost:8080/api/v1/movie/datePassed?search=${currentSearch}`,{
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json',
                },
            })
            if (!response.ok) {
                const error = await response.json();
                if (response.status === 401) {
                    alert('Unauthorized access. Redirecting to login.');
                    window.location.href = '../loginPage/login.html';
                } else {
                    alert(error.message);
                }
                return;
            }

            return await response.json();
        }catch (e){
        console.error('Error fetching watchList:', e);
        return [];
        }
    }

    function createWatchListCard(movie){
        return `
        <div class="movie-card">
        <div class="movie-poster">
            <img src="http://localhost:8080/movieImage/${movie.imagePath}" alt="DeadPool" class="poster-image">
            <button class="remove-btn"  data-movie-id="${movie.id}">
                <i class="fa-solid fa-trash" data-movie-id="${movie.id}"></i>
            </button>
            <button class="edit-btn"  data-movie-id="${movie.id}">
                <i class="fa-solid fa-edit" data-movie-id="${movie.id}"></i>
            </button>
        </div>
            <div class="movie-info">
                <h3 class="movie-title">${movie.title}</h3>
                <div class="movie-details">
                    <span class="show-date">Show Date: ${convertToHumanReadable(movie.viewDate)}</span>
                    <span class="show-time">Show Time: ${convertTo12HourFormat(movie.viewTime)}</span>
                </div>
            </div>
        </div>`;
    }

    async function generateDatePassed(e){
        const  datePassedMovies = await getDatePassedMovies();
        if(datePassedMovies.length === 0){
            const text = `No movie found with name ${currentSearch}`
            const message = "Don't have any movie that passed the current date."
            datePassedGrid.innerHTML =`<p class="no-movie">${currentSearch ? text : message  }</p>`;
            // datePassedGrid.innerHTML = `<p class="no-movie"> don't have any movie that passed the current date.</p>`;
            return
        }
        datePassedGrid.innerHTML= datePassedMovies.map(createWatchListCard).join(' ')
        handleDatePassedDelete();
        handleDatePassedEdit();
    }

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

    function handleDatePassedEdit(){
        const editBtn = document.querySelectorAll('.edit-btn');
        editBtn.forEach((btn) => {
            btn.addEventListener('click',async (e)=>{
                currentSelectedMovieId = e.target.dataset.movieId;
                console.log(currentSelectedMovieId)
                modal.style.display= 'flex'
                try{
                    const response = await fetch(`http://localhost:8080/api/v1/movie/detail/cinema/${currentSelectedMovieId}`,{
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
                    const movie = await response.json();
                    document.getElementById('movie-title').value = movie.title;
                    document.getElementById('movie-description').value=movie.description;
                    document.getElementById('movie-view-time').value = movie.viewTime;
                    document.getElementById('movie-duration').value = convertToString(movie.duration);
                     document.getElementById('movie-view-date').value=movie.viewDate;
                    document.getElementById('movie-total-seats').value=movie.seats;
                     document.getElementById('movie-price').value = movie.price;
                     console.log(document.getElementById('movie-genres').value);
                   document.getElementById('movie-genres').value= movie.genres.join(',');

                }catch (e){
                    alert(e.message)
                }
            })
        })
    }

    function handleDatePassedDelete() {
        const removeBtn = document.querySelectorAll('.remove-btn');
        removeBtn.forEach((btn) => {
            btn.addEventListener('click', async (e) => {
                const movieId = e.target.dataset.movieId;
                try {
                    const response = await fetch(`http://localhost:8080/api/v1/movie/${movieId}`, {
                        method: 'DELETE',
                        headers: {
                            'Authorization': `Bearer ${authToken}`,
                        },
                    });

                    if (!response.ok) {
                        const error = await response.json();
                        if (response.status === 401) {
                            alert('Unauthorized access. Redirecting to login.');
                            window.location.href = '../loginPage/login.html';
                        } else {
                            alert(error.message);
                        }
                        return;
                    }

                    // Remove the card from the DOM
                    const movieCard = btn.closest('.movie-card');
                    if (movieCard) {
                        movieCard.remove();
                    }
                    const remainingCards = document.querySelectorAll('.movie-card');
                    console.log(remainingCards.length)
                    if (remainingCards.length === 0) {
                        datePassedGrid.innerHTML = `<p class="no-movie">No movies have passed the current date</p>`;
                    }


                } catch (e) {
                    console.error('Error deleting watchList:', e);
                    alert("Error deleting watchList");
                }
            });
        });
    }

    async function updateMovie(formData) {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/movie/${currentSelectedMovieId}`, {
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
            const movie = await response.json();
            alert("Movie updated successfully!");
            document.getElementById("add-modal").style.display = "none";
            await generateDatePassed();
        } catch (error) {
            console.error("Error updating movie:", error);
            alert("Error while updating movie");
        }
    }

    document.getElementById("add-movie-form").addEventListener("submit", async (event) => {
        event.preventDefault();

        const minutesDuration = parseInt(document.getElementById('movie-duration').value, 10);
        // const hoursDuration = Math.floor(minutesDuration / 60);
        // const remainingMinutes = minutesDuration % 60;
        console.log(minutesDuration)
        const formatViewTime =convertMinutesToTimeFormat(minutesDuration)
        console.log(formatViewTime)


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

    searchInput.addEventListener('input',async (e)=>{
        currentSearch = e.target.value
        console.log(currentSearch)
        await generateDatePassed();
    })

    await generateDatePassed();

})