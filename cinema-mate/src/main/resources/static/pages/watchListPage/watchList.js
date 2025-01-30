document.addEventListener("DOMContentLoaded",()=>{

    const authToken = localStorage.getItem('authToken')
    const watchlistGrid = document.querySelector('.watchlist-grid');


    function convertTo12HourFormat(timeStr) {
        const [hours, minutes, seconds] = timeStr.split(':'); // Split the string into hours, minutes, and seconds
        let hour = parseInt(hours);
        const ampm = hour >= 12 ? 'PM' : 'AM'; // Determine AM or PM
        hour = hour % 12; // Convert to 12-hour format
        if (hour === 0) hour = 12; // Handle midnight (00:00:00 becomes 12:00 AM)
        return `${hour}:${minutes} ${ampm}`;
    }

    function convertToHumanReadable(dateString) {
        const date = new Date(dateString);
        const months = ["January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"];
        return `${months[date.getMonth()]} ${date.getDate()}, ${date.getFullYear()}`;
    }

    async function getWatchList(){
        try {
            const response = await fetch('http://localhost:8080/api/v1/watchlist',{
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

    function createWatchListCard(watchList){
        return `
        <div class="movie-card">
        <div class="movie-poster">
            <img src="http://localhost:8080/movieImage/${watchList.movieDetailDto.imagePath}" alt="DeadPool" class="poster-image">
            <button class="remove-btn" aria-label="Remove from watchlist" data-watchlist="${watchList.id}">
                <i class="fa-solid fa-trash" data-watchlist="${watchList.id}"></i>
            </button>
        </div>
            <div class="movie-info">
                <h3 class="movie-title">${watchList.movieDetailDto.title}</h3>
                <div class="movie-details">
                    <span class="show-date">Show Date: ${convertToHumanReadable(watchList.movieDetailDto.viewDate)}</span>
                    <span class="show-time">Show Time: ${convertTo12HourFormat(watchList.movieDetailDto.viewTime)}</span>
                </div>
            </div>
        </div>`;
    }

    async function generateWatchLists(e){
        const  watchLists = await getWatchList();
        if(watchLists.length === 0){
            watchlistGrid.innerHTML = `<p class="no-movie"> your don't have any watch list yet.</p>`;
            return
        }
        watchlistGrid.innerHTML= watchLists.map(createWatchListCard).join(' ')
        handleDeleteFromWatchlist();
    }


    function handleDeleteFromWatchlist() {
        const removeBtn = document.querySelectorAll('.remove-btn');
        removeBtn.forEach((btn) => {
            btn.addEventListener('click', async (e) => {
                const watchListId = e.target.dataset.watchlist;
                try {
                    const response = await fetch(`http://localhost:8080/api/v1/watchlist/${watchListId}`, {
                        method: 'DELETE',
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
                        return;
                    }

                    // Remove the card from the DOM
                    const movieCard = btn.closest('.movie-card');
                    if (movieCard) {
                        movieCard.remove();
                    }

                    // Optional: If the grid is empty after deletion, show a message
                    if (watchlistGrid.children.length === 0) {
                        watchlistGrid.innerHTML = `<p class="no-movie">You don't have any watch list yet.</p>`;
                    }
                } catch (e) {
                    console.error('Error deleting watchList:', e);
                    alert("Error deleting watchList");
                }
            });
        });
    }


    generateWatchLists();

})