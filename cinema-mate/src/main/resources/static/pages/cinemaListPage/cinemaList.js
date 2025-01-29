document.addEventListener("DOMContentLoaded",()=>{
    const cinemaGrid = document.getElementById('cinema-grid');
    const authToken = localStorage.getItem('authToken')

    async function fetchCinema(){
        try{
            const response = await fetch('http://localhost:8080/api/v1/cinema/all',{
                method:"GET",
                headers: {
                    'Authorization': `Bearer ${authToken}`, // Add the token to the header
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
                    // alert(`Status: ${response.status}`);
                }
                return;
            }

            const cinemas = await response.json();
            console.log(cinemas)
            return cinemas;

        }catch (e){
            console.error('Error fetching movies:', e);
            return [];
        }
    }


    // Function to create movie cards
    function createCinemaCard(cinema) {
        const path = cinema.imagePath;
        return `
        <div class="cinema-card" id="${cinema.id}">
            ${path === null ?
            `<img src="../no-profile.png" alt="${cinema.cinemaName}" class="cinema-image"/>` :
            `<img src="http://localhost:8080/cinemaProfile/${cinema.imagePath}" alt="${cinema.cinemaName}" class="cinema-image"/>`
        }
            <div class="cinema-info">
                <h3 class="cinema-title">Title: ${cinema.cinemaName}</h3>
            </div>
        </div>
    `;
    }


    async function generateCinemaCard() {
        const cinemas = await fetchCinema();
        if (cinemas.length === 0) {
            // console.log("dkkdkdkd")
            cinemaGrid.innerHTML = '<p class="no-cinema">No cinema available to display.</p>';
        }
        cinemaGrid.innerHTML = cinemas.map(movie => createCinemaCard(movie)).join('');
        handelRedirect();
    }

    function handelRedirect(){
        const cinemaCard = document.querySelectorAll(".cinema-card");
        cinemaCard.forEach((cinema)=>{
            cinema.addEventListener('click',(e)=>{
                const cinemaId = e.currentTarget.id;
                window.location.href = `../userHomePage/userHome.html?cinemaId=${cinemaId}`
            })
        })
    }


    generateCinemaCard();

})