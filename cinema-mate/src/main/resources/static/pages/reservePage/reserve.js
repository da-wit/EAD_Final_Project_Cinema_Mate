// document.addEventListener('DOMContentLoaded', async () => {
//
//     const bookedList = document.getElementById('booked-list');
//     const authToken = localStorage.getItem('authToken');
//
//     function convertTo12HourFormat(timeStr) {
//         const [hours, minutes, seconds] = timeStr.split(':'); // Split the string into hours, minutes, and seconds
//         let hour = parseInt(hours);
//         const ampm = hour >= 12 ? 'PM' : 'AM'; // Determine AM or PM
//         hour = hour % 12; // Convert to 12-hour format
//         if (hour === 0) hour = 12; // Handle midnight (00:00:00 becomes 12:00 AM)
//
//         return `${hour}:${minutes} ${ampm}`;
//     }
//
//     async function fetchBookedMovies(){
//         try{
//             const response = await fetch('http://localhost:8080/api/v1/booking/user',{
//                 method:"GET",
//                 headers: {
//                     'Authorization': `Bearer ${authToken}`,
//                     'Content-Type': 'application/json'
//                 },
//             })
//
//             if(!response.ok){
//                 const error = await response.json();
//                 if (response.status === 401) {
//                     alert("Unauthorized access. Redirecting to login.");
//                     window.location.href = '../loginPage/login.html';
//                 } else {
//                     alert(error.message);
//                 }
//             }
//             return await response.json();
//
//         }catch (error){
//             console.error('Error while booking movies:', error);
//             return [];
//         }
//     }
//
//
//
//     function createBookedCard(booked){
//         return `<div class="movie-info" id="${booked.id}">
//                 <img src="http://localhost:8080/movieImage/${booked.movieDetailDto.imagePath}" alt="Movie Poster" class="movie-poster">
//                 <div class="card-content">
//                     <div class="info-item">
//                         <h2>Title</h2>
//                         <p>${booked.movieDetailDto.title}</p>
//                     </div>
//                     <div class="info-item">
//                         <h2>Show Date</h2>
//                         <p>${booked.movieDetailDto.viewDate}</p>
//                     </div>
//                     <div class="info-item">
//                         <h2>Show Time</h2>
//                        <p>${convertTo12HourFormat(booked.movieDetailDto.viewTime)}</p>
//                     </div>
//                     <div class="info-item">
//                         <h2>Cinema Name</h2>
//                        <p>${booked.movieDetailDto.cinema.cinemaName}</p>
//                     </div>
//                     <div class="info-item">
//                         <h2>Number of Reserved Seat</h2>
//                         <p>${booked.numberOfSeats}</p>
//                     </div>
//
//                     <div class="info-item">
//                         <h2>Total price</h2>
//                         <p>${booked.totalPrice} Birr</p>
//                     </div>
//                    <div class="ticket">
//                        <div class="info-item">
//                            <h2>Ticket code</h2>
//                            <p>${booked.bookingCode}</p>
//                        </div>
//                        <div class="action-buttons">
//                            <button class="btn update-btn">Update</button>
//                            <button class="btn delete-btn">Delete</button>
//                        </div>
//                    </div>
//                 </div>
//             </div>`;
//     }
//
//     async function generateBookedCard(){
//         const booked = await fetchBookedMovies();
//         console.log(booked)
//
//         if(booked.length === 0 ){
//
//             const message  = "Have not booked yet";
//             bookedList.innerHTML =`<p class="no-movie">${message}</p>`;
//             return;
//         }
//         bookedList.innerHTML = booked.map((book) => createBookedCard(book)).join(' ')
//         handleFormVisibility()
//     }
//
//     generateBookedCard()
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//     const modal = document.getElementById('reservationModal');
//     // const reserveButton = document.querySelector('.update-btn');
//     const cancelButton = document.querySelector('.cancel-btn');
//     const maxSeats = document.getElementById('seats-info')
//     const seatInput = document.getElementById('seats')
//     const errorSpan = document.getElementById('error')
//     const reservationForm = document.getElementById('reservationForm');
//
//
//
//
//
//     function handleFormVisibility(){
//         const cancelButton = document.querySelector('.cancel-btn');
//         const maxSeats = document.getElementById('seats-info')
//         const seatInput = document.getElementById('seats')
//         const errorSpan = document.getElementById('error')
//         const reservationForm = document.getElementById('reservationForm');
//         const reserveButton = document.querySelectorAll('.update-btn');
//
//         const initialSeats =  ;
//         // const initialSeats =  movie.seats - movie.bookedSeats;
//
//         // let availableSeats = movie.seats - movie.bookedSeats;
//         let availableSeats =;
//
//         maxSeats.textContent = `Maximum available ${availableSeats} seats`;
//
//         reserveButton.forEach((reserve) =>{
//             reserve.addEventListener('click',(e)=>{
//                 modal.classList.add('active');
//             })
//         })
//
//         cancelButton.addEventListener('click', () => {
//             modal.classList.remove('active');
//             seatInput.value =0;
//             maxSeats.textContent = `Maximum available ${initialSeats} seats`;
//         });
//
//
//         modal.addEventListener('click', (event) => {
//             if (event.target === modal) {
//                 modal.classList.remove('active');
//                 seatInput.value =0;
//                 maxSeats.textContent = `Maximum available ${initialSeats} seats`;
//             }
//         });
//         seatInput.addEventListener('input', () => {
//             let enteredSeats = parseInt(seatInput.value, 10);
//
//             if (isNaN(enteredSeats) || enteredSeats <= 0) {
//                 errorSpan.textContent = 'Please enter a valid number of seats.';
//                 errorSpan.style.display = 'block';
//                 maxSeats.textContent = `Maximum available ${initialSeats} seats`;
//                 availableSeats=initialSeats;
//                 return;
//             }
//             if (enteredSeats > availableSeats) {
//
//                 errorSpan.textContent = 'Number exceeds available seats!';
//                 errorSpan.style.display = 'block';
//                 maxSeats.textContent = `Maximum available ${initialSeats} seats`;
//             } else {
//
//                 errorSpan.style.display = 'none';
//                 availableSeats -= enteredSeats;
//                 maxSeats.textContent = `Maximum available ${availableSeats} seats`;
//                 availableSeats += enteredSeats
//
//             }
//         });
//         // reserveButton.addEventListener('click', () => {
//         //
//         //     modal.classList.add('active');
//         // });
//     }
//
//
//
//     // cancelButton.addEventListener('click', () => {
//     //     modal.classList.remove('active');
//     //     seatInput.value =0;
//     //     maxSeats.textContent = `Maximum available ${initialSeats} seats`;
//     // });
//     //
//     //
//     // modal.addEventListener('click', (event) => {
//     //     if (event.target === modal) {
//     //         modal.classList.remove('active');
//     //         seatInput.value =0;
//     //         maxSeats.textContent = `Maximum available ${initialSeats} seats`;
//     //     }
//     // });
//
//
//
//
// // Update the available seats dynamically based on user input
// //     seatInput.addEventListener('input', () => {
// //         let enteredSeats = parseInt(seatInput.value, 10);
// //
// //         if (isNaN(enteredSeats) || enteredSeats <= 0) {
// //             errorSpan.textContent = 'Please enter a valid number of seats.';
// //             errorSpan.style.display = 'block';
// //             maxSeats.textContent = `Maximum available ${initialSeats} seats`;
// //             availableSeats=initialSeats;
// //             return;
// //         }
// //         if (enteredSeats > availableSeats) {
// //
// //             errorSpan.textContent = 'Number exceeds available seats!';
// //             errorSpan.style.display = 'block';
// //             maxSeats.textContent = `Maximum available ${initialSeats} seats`;
// //         } else {
// //
// //             errorSpan.style.display = 'none';
// //             availableSeats -= enteredSeats;
// //             maxSeats.textContent = `Maximum available ${availableSeats} seats`;
// //             availableSeats += enteredSeats
// //
// //         }
// //     });
//
//     reservationForm.addEventListener('submit',async (e) => {
//         e.preventDefault();
//         let enteredSeats = parseInt(seatInput.value,10);
//         if (isNaN(enteredSeats) || enteredSeats <= 0 || enteredSeats > availableSeats) {
//             errorSpan.textContent = 'Please enter a valid number of seats or number exceeds available seats!';
//             errorSpan.style.display = 'block';
//             return;
//         }
//
//         const data = {
//             seats:enteredSeats
//         }
//
//         try{
//             const response = await fetch(`http://localhost:8080/api/v1/booking/${movieId}`,{
//                 method:"POST",
//                 headers: {
//                     'Authorization': `Bearer ${authToken}`,
//                     'Content-Type': 'application/json'
//                 },
//                 body:JSON.stringify(data)
//
//             })
//             if(!response.ok){
//                 const error = await response.json();
//                 if (response.status === 401) {
//                     alert("Unauthorized access. Redirecting to login.");
//                     window.location.href = '../loginPage/login.html';
//                 } else {
//                     alert(error.message);
//                 }
//             }
//             modal.classList.remove('active');
//             seatInput.value =0;
//             maxSeats.textContent = `Maximum available ${availableSeats-enteredSeats} seats`;
//             const movie = await fetchMovieDetail();
//             const seats = document.getElementById('available-seats');
//             seats.innerText = movie.seats - movie.bookedSeats;
//             reserveButton.innerHTML = "Reserved"
//             reserveButton.disabled = true;
//             // renderMovieDetails(movie);
//
//         }catch (error){
//             console.error('Error while booking movies:', error);
//             alert("Error while reserving a seat")
//         }
//     });
//
// });


document.addEventListener('DOMContentLoaded', async () => {
    const bookedList = document.getElementById('booked-list');
    const authToken = localStorage.getItem('authToken');
    const modal = document.getElementById('reservationModal');
    const cancelButton = document.querySelector('.cancel-btn');
    const maxSeats = document.getElementById('seats-info');
    const seatInput = document.getElementById('seats');
    const errorSpan = document.getElementById('error');
    const reservationForm = document.getElementById('reservationForm');
    let availableSeats = 0;
    let movieId = null; // Dynamically set this when opening the modal.

    // Convert 24-hour time to 12-hour format
    function convertTo12HourFormat(timeStr) {
        const [hours, minutes] = timeStr.split(':');
        let hour = parseInt(hours, 10);
        const ampm = hour >= 12 ? 'PM' : 'AM';
        hour = hour % 12 || 12;
        return `${hour}:${minutes} ${ampm}`;
    }

    function convertToHumanReadable(dateString) {
        const date = new Date(dateString);
        const months = ["January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"];
        return `${months[date.getMonth()]} ${date.getDate()}, ${date.getFullYear()}`;
    }
    // Fetch booked movies
    async function fetchBookedMovies() {
        try {
            const response = await fetch('http://localhost:8080/api/v1/booking/user', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json',
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
                return [];
            }

            return await response.json();
        } catch (error) {
            console.error('Error fetching booked movies:', error);
            return [];
        }
    }

    // Create a booked movie card
    function createBookedCard(booked) {
        return `
            <div class="movie-info" id="${booked.id}">
                <img src="http://localhost:8080/movieImage/${booked.movieDetailDto.imagePath}" alt="Movie Poster" class="movie-poster">
                <div class="card-content">
                    <div class="info-item">
                        <h2>Title</h2>
                        <p>${booked.movieDetailDto.title}</p>
                    </div>
                    <div class="info-item">
                        <h2>Show Date</h2>
                        <p>${convertToHumanReadable(booked.movieDetailDto.viewDate)}</p>
                    </div>
                    <div class="info-item">
                        <h2>Show Time</h2>
                        <p>${convertTo12HourFormat(booked.movieDetailDto.viewTime)}</p>
                    </div>
                    <div class="info-item">
                        <h2>Cinema Name</h2>
                        <p>${booked.movieDetailDto.cinema.cinemaName}</p>
                    </div>
                    <div class="info-item">
                        <h2>Number of Reserved Seats</h2>
                        <p>${booked.numberOfSeats}</p>
                    </div>
                    <div class="info-item">
                        <h2>Total Price</h2>
                        <p>${booked.totalPrice} Birr</p>
                    </div>
                    <div class="ticket">
                        <div class="info-item">
                            <h2>Ticket Code</h2>
                            <p>${booked.bookingCode}</p>
                        </div>
                        <div class="action-buttons">
                            <button class="btn update-btn" data-book-id="${booked.id}" data-available-seats="${booked.movieDetailDto.seats - booked.movieDetailDto.bookedSeats + booked.numberOfSeats}" data-booked-seats="${booked.numberOfSeats}">Update</button>
                            <button class="btn delete-btn" data-book-id="${booked.id}">Delete</button>
                        </div>
                    </div>
                </div>
            </div>`;
    }

    // Generate booked cards
    async function generateBookedCard() {
        const bookedMovies = await fetchBookedMovies();

        if (bookedMovies.length === 0) {
            bookedList.innerHTML = `<p class="no-movie">You have not reserved any movies yet.</p>`;
            return;
        }

        bookedList.innerHTML = bookedMovies.map(createBookedCard).join('');
        handleUpdateButtons();
        handleDelete()
    }

    function  handleDelete(){
        const deleteButtons = document.querySelectorAll('.delete-btn');

        deleteButtons.forEach((button) => {
            button.addEventListener('click',async (e) => {
                const bookId = e.target.dataset.bookId;
                console.log(bookId)
                try{

                    const response = await fetch(`http://localhost:8080/api/v1/booking/${bookId}`,{
                        method: 'DELETE',
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

                    await generateBookedCard();
                } catch (error) {
                    console.error('Error fetching booked movies:', error);
                    alert("Error while deleting reservation");
                };

            })
        })
    }

    // Handle Update button click
    function handleUpdateButtons() {
        const updateButtons = document.querySelectorAll('.update-btn');

        updateButtons.forEach((button) => {
            button.addEventListener('click', (e) => {
                movieId = e.target.dataset.bookId;
                console.log(e.target.dataset.bookedSeats)
                console.log("dkdkdkkdkdkdkd")
                availableSeats = parseInt(e.target.dataset.availableSeats, 10);
                bookedSeats = parseInt(e.target.dataset.bookedSeats,10);
                console.log(availableSeats)
                maxSeats.textContent = `Maximum available ${availableSeats} seats`;
                document.getElementById('seats').value = bookedSeats
                // seatInput.value = bookedSeats;
                errorSpan.style.display = 'none';
                modal.classList.add('active');
            });
        });
    }

    // Close modal
    cancelButton.addEventListener('click', () => {
        modal.classList.remove('active');
    });

    modal.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.classList.remove('active');
        }
    });

    // Validate seat input
    seatInput.addEventListener('input', () => {
        const enteredSeats = parseInt(seatInput.value, 10);

        if (isNaN(enteredSeats) || enteredSeats <= 0) {
            errorSpan.textContent = 'Please enter a valid number of seats.';
            errorSpan.style.display = 'block';
            return;
        }

        if (enteredSeats > availableSeats) {
            errorSpan.textContent = 'Number exceeds available seats!';
            errorSpan.style.display = 'block';
        } else {
            errorSpan.style.display = 'none';
        }
    });

    // Handle reservation form submission
    reservationForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const enteredSeats = parseInt(seatInput.value, 10);

        if (isNaN(enteredSeats) || enteredSeats <= 0 || enteredSeats > availableSeats) {
            errorSpan.textContent = 'Please enter a valid number of seats or number exceeds available seats!';
            errorSpan.style.display = 'block';
            return;
        }

        const data = { seats: enteredSeats };

        try {
            const response = await fetch(`http://localhost:8080/api/v1/booking/${movieId}`, {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (!response.ok) {
                const error = await response.json();
                alert(error.message);
                return;
            }

            modal.classList.remove('active');
            await generateBookedCard();
        } catch (error) {
            console.error('Error updating reservation:', error);
        }
    });

    // Initialize booked movies
    await generateBookedCard();
});
