document.addEventListener('DOMContentLoaded', async () => {
    const authToken = localStorage.getItem('authToken');
    const reservationsList = document.getElementById('reservationsList');
    const verifyBtn = document.getElementById("verify-button");
    const modal = document.getElementById("verify-modal");
    const closeButton = document.getElementById("close-button");
    const verifyUser = document.getElementById("verify-user-form");
    let username = document.getElementById('username');
    let code = document.getElementById('booking-code');
    const verificationModal = document.getElementById('verification-display-modal');
    const totalReservedMovies = document.querySelector('.total-reserved-movies');

    // Convert 24-hour time to 12-hour format
    function convertTo12HourFormat(timeStr) {
        const [hours, minutes] = timeStr.split(':');
        let hour = parseInt(hours);
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
            console.error('Error fetching cinema booking:', error);
            alert("Error while fetching cinema booking");
        }
    }

    async function generateAll() {
        const reservedMovies = await getCinemaBooking();
        totalReservedMovies.textContent = reservedMovies.length;
        if (!reservedMovies || reservedMovies.length === 0) {
            reservationsList.innerHTML = `<p class="no-movie">No one has reserved yet</p>`;
            verifyBtn.style.display ='none';
            return;
        }
        reservationsList.innerHTML = reservedMovies.map(reserved => generateCard(reserved)).join('');
    }

    function generateCard(reserved) {
        return `
        <div class="reservation-card">
            <img src="${reserved.movieDto.imagePath ? `http://localhost:8080/movieImage/${reserved.movieDto.imagePath}` : "../loginPage/2827349.jpg"}" alt="${reserved.userName}" class="movie-poster">
            <div class="reservation-details">
                <div class="user-info">
                    <img src="${reserved.imagePath ? `http://localhost:8080/userProfile/${reserved.imagePath}` : "../no-user-profile.png"}" alt="${reserved.userName}" class="user-avatar">
                    <span class="user-name">${reserved.userName}</span>
                </div>
                <h2 class="movie-title">${reserved.movieDto.title}</h2>
                <div class="movie-info">
                    <div class="info-item"><div class="info-label">Show Date</div><div class="info-value">${convertToHumanReadable(reserved.movieDto.viewDate)}</div></div>
                    <div class="info-item"><div class="info-label">Show Time</div><div class="info-value">${convertTo12HourFormat(reserved.movieDto.viewTime)}</div></div>
                    <div class="info-item"><div class="info-label">Duration</div><div class="info-value">${convertToDurationFormat(reserved.movieDto.duration)}</div></div>
                    <div class="info-item"><div class="info-label">Seats Reserved</div><div class="info-value">${reserved.bookedSeats}</div></div>
                </div>
                <div class="total-price">Total Price: ${reserved.totalPrice.toLocaleString()} Birr</div>
            </div>
        </div>`;
    }

    // Open Verify Modal
    verifyBtn.addEventListener("click", () => {
        modal.style.display = "flex";
        verifyUser.addEventListener('submit', verifyUserHandler);
    });

    // Close Verify Modal
    closeButton.addEventListener("click", () => {
        closeVerifyModal();
    });

    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            closeVerifyModal();
        }
    });

    function closeVerifyModal() {
        modal.style.display = "none";
        username.value = '';
        code.value = '';
        verifyUser.removeEventListener('submit', verifyUserHandler);
    }

    async function verifyUserHandler(e) {
        e.preventDefault();
        const userName = username.value.trim();
        const bookingCode = code.value.trim();

        const data = { userName, bookingCode };

        try {
            const response = await fetch('http://localhost:8080/api/v1/booking/verify', {
                method: "POST",
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });

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
            const reserve = await response.json();
            displayReservation(reserve);

        } catch (error) {
            console.error('Error verifying reservation:', error);
            alert(error.message);
        }
    }

    function displayReservation(reserved) {
        const reservationContainer = document.getElementById('verification-display-modal');
        reservationContainer.innerHTML = `
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
                    <div class="info-item"><div class="info-label">Show Date</div><div class="info-value">${convertToHumanReadable(reserved.movieDto.viewDate)}</div></div>
                    <div class="info-item"><div class="info-label">Show Time</div><div class="info-value">${convertTo12HourFormat(reserved.movieDto.viewTime)}</div></div>
                    <div class="info-item"><div class="info-label">Duration</div><div class="info-value">${convertToDurationFormat(reserved.movieDto.duration)}</div></div>
                    <div class="info-item"><div class="info-label">Seats Reserved</div><div class="info-value">${reserved.bookedSeats}</div></div>
                </div>
                <div class="total-price">Total Price: ${reserved.totalPrice.toLocaleString()} Birr</div>
            </div>
        </div>`;
        verificationModal.style.display = 'flex';
    }

    window.addEventListener("click", (event) => {
        if (event.target === verificationModal) {
            verificationModal.style.display = "none";
            username.value = '';
            code.value = '';

        }
    });

    await generateAll();
});
