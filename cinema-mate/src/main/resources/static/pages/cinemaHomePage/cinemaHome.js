document.addEventListener("DOMContentLoaded",()=>{
    const movieGrid = document.getElementById('movie-grid');
    const authToken = localStorage.getItem('authToken')
    const searchInput = document.getElementById("search-input"); // Add search input field reference
    let currentSearch ="";


    function convertMinutesToTimeFormat(totalMinutes) {
        const hours = Math.floor(totalMinutes / 60); // Get the hours
        const minutes = totalMinutes % 60; // Get the remaining minutes

        // Format hours and minutes to always show two digits (e.g., 01:50)
        const formattedHours = String(hours).padStart(2, '0');
        const formattedMinutes = String(minutes).padStart(2, '0');

        return `${formattedHours}:${formattedMinutes}:00`;


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


    const addButton = document.getElementById("add-button");
    const modal = document.getElementById("add-modal");
    const closeButton = document.getElementById("close-button");
    const addMovie = document.getElementById("add-movie-form");


    addButton.addEventListener("click", () => {
        modal.style.display = "flex";
    });

    closeButton.addEventListener("click", () => {
        modal.style.display = "none";
    });


    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });



    // const title = document.getElementById('movie-title').value;
    // const description = document.getElementById('movie-description').value;
    // const viewTime = document.getElementById('movie-view-time').value;
    // const duration = document.getElementById('movie-duration').value;
    // const viewDate = document.getElementById('movie-view-date').value;
    // const totalSeats = document.getElementById('movie-total-seats').value;
    // const pricePerSeat = document.getElementById('movie-price').value;
    // const genres = document.getElementById('movie-genres').value.split(',');

    // const createMovieDto =
    //     {
    //         "title":title,
    //         "description":description,
    //         "genres":genres,
    //         "viewTime":viewTime,
    //         "viewDate":viewDate,
    //         "duration":duration,
    //         "price":pricePerSeat,
    //         "seats":totalSeats
    //     };
    // console.log(createMovieDto)

    async function createMovie(formData){
            try {
                    const response = await fetch("http://localhost:8080/api/v1/movie", {
                        method: "POST",
                        body: formData,
                        headers: {
                            'Authorization': `Bearer ${authToken}`,
                        },
                    });

                    if (!response.ok) {
                        const errorData = await response.json();
                        await (`Error: ${response.status} - ${errorData.message}`);
                        return;
                    }

                    const result = await response.json();
                    alert("Movie added successfully!");
                    await generateMovieCard()
                    modal.style.display = "none";
                    console.log("Movie created successfully:", result);
                } catch (error) {
                    console.error("Error creating movie:", error.message);
                    alert("Error while create movie")
                }
    }

    addMovie.addEventListener("submit", async (event) => {
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
        formData.append("createMovieDto", new Blob([JSON.stringify(createMovieDto)], { type: "application/json" }));
        formData.append("image", imageFile);

        await createMovie(formData)

        // modal.style.display = "none";

    });




    async function fetchMovies(){
        const fetchUrl = `http://localhost:8080/api/v1/movie/cinema?search=${currentSearch}`;
        // fromCinema
        //     ? `http://localhost:8080/api/v1/movie/cinema/${cinemaId}?search=${currentSearch}`
        //     : `http://localhost:8080/api/v1/movie?search=${currentSearch}`;

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
            // const message = fromCinema
            //     ? "This cinema currently has no movies listed. Please check back later!"
            //     : "No movies are currently available. Explore more options soon!";
            const text = `No movie found with name ${currentSearch}`
            // movieGrid.innerHTML =`<p class="no-movie">${currentSearch ? text : message  }</p>`;
            movieGrid.innerHTML =`<p class="no-movie">You have not added any movies yet</p>`;
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
                window.location.href = `../CinemaMovieDetailPage/CinemaMovieDetail.html?movieId=${movieId}`;
            })
        })
    }

    searchInput.addEventListener('input',(e)=>{
        currentSearch = e.target.value
        console.log(currentSearch)
        generateMovieCard();
    })


    // document.getElementById('add-movie-form').addEventListener('submit',  async (e)=> {
    //     e.preventDefault(); // Prevent form from submitting normally
    //
    //     // Create FormData object to send multipart form data
    //      // Get auth token from local storage
    //     // const formData = new FormData();
    //
    //     const createMovieDto = {"title": "new title", "description": "Descfdsggggggggggggggggggggggggggggggggggggggggggggggggription", "duration": "02:00:00","viewTime":"03:30:00",
    //         "price":"120.50","genres":[],"viewDate": "2025-02-01", "seats": 100};
    //
    //     // Get the file input (e.g., from a file input element)
    //     const imageFile = document.querySelector('input[type="file"]').files[0]; // File from <input type="file" />
    //
    //     // Create a FormData object
    //     const formData = new FormData();
    //     formData.append("createMovieDto", new Blob([JSON.stringify(createMovieDto)], { type: "application/json" }));
    //     formData.append("image", imageFile);

        // try {
        //     const response = await fetch("http://localhost:8080/api/v1/movie", {
        //         method: "POST",
        //         body: formData,
        //         headers: {
        //             'Authorization': `Bearer ${authToken}`,
        //         },
        //     });
        //
        //     if (!response.ok) {
        //         const errorData = await response.json();
        //         await (`Error: ${response.status} - ${errorData.message}`);
        //         return;
        //     }
        //
        //     const result = await response.json();
        //     console.log("Movie created successfully:", result);
        // } catch (error) {
        //     console.error("Error creating movie:", error.message);
        // }


    // });
    // document.getElementById('add-movie-form').addEventListener('submit', function (e) {
    //     e.preventDefault(); // Prevent form from submitting normally
    //
    //     // Create FormData object to send multipart form data
    //     const formData = new FormData();
    //
    //     // Collect form data
    //     const title = document.getElementById('movie-title').value;
    //     const description = document.getElementById('movie-description').value;
    //     const viewTime = document.getElementById('movie-view-time').value;
    //     const duration = document.getElementById('movie-duration').value;
    //     const viewDate = document.getElementById('movie-view-date').value;
    //     const totalSeats = document.getElementById('movie-total-seats').value;
    //     const pricePerSeat = document.getElementById('movie-price').value;
    //     const genres = [];
    //
    //     // Create createMovieDto object
    //     const createMovieDto = {
    //         title: title,
    //         description: description,
    //         viewTime: viewTime,
    //         duration: duration,
    //         viewDate: viewDate,
    //         totalSeats: totalSeats,
    //         pricePerSeat: pricePerSeat,
    //         genres: []
    //     };

        // Append the createMovieDto as a JSON string with the correct Content-Type
        // const createMovieDtoBlob = new Blob([JSON.stringify(createMovieDto)], {
        //     type: 'application/json' // Explicitly set the Content-Type
        // });
        // formData.append('createMovieDto', createMovieDtoBlob, 'createMovieDto.json');
        //
        // // Append the image file
        // const imageFile = document.getElementById('movie-image').files[0];
        // // if (imageFile) {
        // //     formData.append('image', imageFile);
        // // }
        //
        // // Get the auth token from local storage
        // const authToken = localStorage.getItem('authToken');
        // try{
        //
        // }

        // Send the POST request
    //     fetch('http://localhost:8080/api/v1/movie', {
    //         method: 'POST',
    //         headers: {
    //             'Authorization': `Bearer ${authToken}`,
    //         },
    //         body: formData, // Send FormData (this automatically sets Content-Type to multipart/form-data)
    //     })
    //         .then(response => {
    //             if (!response.ok) {
    //                 return response.json().then(errorData => {
    //                     throw new Error(errorData.message || 'Error adding movie');
    //                 });
    //             }
    //             return response.json();
    //         })
    //         .then(data => {
    //             alert('Movie added successfully!');
    //             closeModal(); // Close the modal after successful submission
    //             generateMovieCard(); // Refresh the movie list or perform other actions
    //         })
    //         .catch(error => {
    //             console.error('Error:', error);
    //             alert(error.message || 'There was an error adding the movie.');
    //         });
    // });

// // Function to close the modal (you can customize this)
//     function closeModal() {
//         document.querySelector('.modal').style.display = 'none';
//     }

    generateMovieCard();

})