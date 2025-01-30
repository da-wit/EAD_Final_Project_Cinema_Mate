document.addEventListener("DOMContentLoaded",()=>{
    const cinemaGrid = document.getElementById('cinema-grid');
    const authToken = localStorage.getItem('authToken')

    const searchInput = document.getElementById("search-input"); // Add search input field reference
    let currentSearch ="";

    async function fetchCinema(){
        try{
            const response = await fetch(`http://localhost:8080/api/v1/cinema/all?search=${currentSearch}`,{
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
            `<img src="../no-cinema-profile.png" alt="${cinema.cinemaName}" class="cinema-image"/>` :
            `<img src="http://localhost:8080/cinemaProfile/${cinema.imagePath}" alt="${cinema.cinemaName}" class="cinema-image"/>`
        }
            <div class="cinema-info">
                <h3 class="cinema-title">Cinema name: ${cinema.cinemaName.charAt(0).toUpperCase() + cinema.cinemaName.slice(1).toLowerCase()}</h3>
            </div>
        </div>
    `;
    }


    async function generateCinemaCard() {
        const cinemas = await fetchCinema();
        if (cinemas.length === 0) {
            console.log(currentSearch)
            const text = `No cinema found with name ${currentSearch}`
            const message = "No cinema available to display."
            cinemaGrid.innerHTML =`<p class="no-cinema">${currentSearch ? text : message  }</p>`;
            return;
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

    searchInput.addEventListener('input',(e)=>{
        currentSearch = e.target.value
        console.log(currentSearch)
        generateCinemaCard();
    })


    generateCinemaCard();

})