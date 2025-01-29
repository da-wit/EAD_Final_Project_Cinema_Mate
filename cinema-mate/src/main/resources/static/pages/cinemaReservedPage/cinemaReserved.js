document.addEventListener('DOMContentLoaded', async () => {
    const authToken = localStorage.getItem('authToken')
    const reservationsList = document.getElementById('reservationsList');

    function convertTo12HourFormat(timeStr) {
        const [hours, minutes, seconds] = timeStr.split(':'); // Split the string into hours, minutes, and seconds
        let hour = parseInt(hours);
        const ampm = hour >= 12 ? 'PM' : 'AM'; // Determine AM or PM
        hour = hour % 12; // Convert to 12-hour format
        if (hour === 0) hour = 12; // Handle midnight (00:00:00 becomes 12:00 AM)

        return `${hour}:${minutes} ${ampm}`;
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

    function convertToDurationFormat(timeStr) {
        const [hours, minutes] = timeStr.split(':');
        return `${hours}:${minutes} hr`;
    }


    async function getCinemaBooking() {
        try {
            const response = await fetch('http://localhost:8080/api/v1/booking/cinema', {
                method: "GET",
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                }
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

            return await response.json();
        } catch (error) {
            console.error('Error fetching while:', error);
            alert("Error while fetching cinema booking")
        }
    };

    async function generateaAll() {
        const reservedMovies = await getCinemaBooking();

        if (reservedMovies.length === 0) {
            reservationsList.innerHTML = `<p class="no-movie">No one has reserved yet</p>`;
            return;
        }
        reservationsList.innerHTML = reservedMovies.map(reserved => generateCard(reserved)).join('');
    }

    function generateCard(reserved) {
        return `<div class="reservation-card">
            <img src="${reserved.movieDto.imagePath ? `http://localhost:8080/movieImage/${reserved.movieDto.imagePath}` : "../loginPage/2827349.jpg"}" alt="${reserved.userName}" class="movie-poster">
            <div class="reservation-details">
                <div class="user-info">
                    <img src="${reserved.imagePath ? `http://localhost:8080/userProfile/${reserved.imagePath}` : "../loginPage/2827349.jpg"}" alt="${reserved.userName}" class="user-avatar">
                    <span class="user-name">${reserved.userName}</span>
                </div>

                <h2 class="movie-title">${reserved.movieDto.title}</h2>

                <div class="movie-info">
                    <div class="info-item">
                        <div class="info-label">Show Date</div>
                        <div class="info-value">${convertToHumanReadable(reserved.movieDto.viewDate)}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Show Time</div>
                        <div class="info-value">${convertTo12HourFormat(reserved.movieDto.viewTime)}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Duration</div>
                        <div class="info-value">${convertToDurationFormat(reserved.movieDto.duration)}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Seats Reserved</div>
                        <div class="info-value">${reserved.bookedSeats}</div>
                    </div>
                </div>

                <div class="total-price">
                    Total Price: ${reserved.totalPrice} Birr
                </div>
            </div>
        </div>`
    }

    const verifyBtn = document.getElementById("verify-button");
    const modal = document.getElementById("verify-modal");
    const closeButton = document.getElementById("close-button");
    const verifyUser = document.getElementById("verify-user-form");
    let username = document.getElementById('username')
    let code = document.getElementById('booking-code')
    const verificationModal = document.getElementById('verification-display-modal');


    verifyBtn.addEventListener("click", () => {
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

    verifyUser.addEventListener('submit', async (e) => {
        e.preventDefault()

        const userName = username.value.trim();
        const bookingCode = code.value.trim();

        const data = {
            userName: userName,
            bookingCode: bookingCode
        }

        try {
            const response = await fetch('http://localhost:8080/api/v1/booking/verify',
                {
                    method: "POST",
                    headers: {
                        'Authorization': `Bearer ${authToken}`,
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                })

            if (!response.ok) {
                const error = await response.json();
                if (response.status === 401) {
                    alert("Unauthorized access. Redirecting to login.");
                    window.location.href = '../loginPage/login.html';
                } else {
                    throw new Error(error.message);
                }
            }

            modal.style.display = 'none';

            const reserve =  await response.json();
            verificationModal.style.display = 'flex';
            displayReservation(reserve);

        } catch (error) {
            console.error('Error verifying reservation:', error);
            alert(error.message)
        }
    })


    function displayReservation(reserved) {
        const reservationContainer = document.getElementById('verification-display-modal'); // Ensure you have this div in your HTML


        const showDate = convertToHumanReadable(reserved.movieDto.viewDate);
        const showTime = convertTo12HourFormat(reserved.movieDto.viewTime);


        const reservationCard = `
        <div class="reservation-card">
            <img src="${reserved.movieDto.imagePath ? `http://localhost:8080/movieImage/${reserved.movieDto.imagePath}` : '../loginPage/2827349.jpg'}"
                alt="${reserved.userName}" class="movie-poster">
            
            <div class="reservation-details">
                <div class="user-info">
                    <img src="${reserved.imagePath ? `http://localhost:8080/userProfile/${reserved.imagePath}` : '../loginPage/2827349.jpg'}"
                        alt="${reserved.userName}" class="user-avatar">
                    <span class="user-name">${reserved.userName}</span>
                </div>

                <h2 class="movie-title">${reserved.movieDto.title}</h2>

                <div class="movie-info">
                    <div class="info-item">
                        <div class="info-label">Show Date</div>
                        <div class="info-value">${showDate}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Show Time</div>
                        <div class="info-value">${showTime}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Duration</div>
                        <div class="info-value">${convertToDurationFormat(reserved.movieDto.duration)}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Seats Reserved</div>
                        <div class="info-value">${reserved.bookedSeats}</div>
                    </div>
                </div>

                <div class="total-price">
                    Total Price: ${reserved.totalPrice} Birr
                </div>
            </div>
        </div>
    `;
        reservationContainer.innerHTML = reservationCard;
        username=''
        code=''
        window.addEventListener("click", (event) => {
            if (event.target === verificationModal) {
                verificationModal.style.display = "none";
            }
        });
    }

    await generateaAll();


});


