document.addEventListener('DOMContentLoaded',async ()=>{
    const url = new URLSearchParams(window.location.search);
    const cinemaId = url.get('cinemaId');
    const authToken = localStorage.getItem('authToken')
    const cinemaInfo = document.querySelector(".cinema-info")

    async function fetchCinemaDetail(){
        try{
            const response = await fetch(`http://localhost:8080/api/v1/cinema/${cinemaId}`,{
                method:"GET",
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                }
            });

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
            console.error('Error fetching cinema detail:', error);
            alert("Error while fetching cinema detail")
        }
    }

    const cinema = await fetchCinemaDetail();
    const cinemaDetailHtml = `
     <div class="cinema-image">
        <img src="${cinema.imagePath ? `http://localhost:8080/cinemaProfile/${cinema.imagePath}` : '../no-cinema-profile.png'}"  alt="${cinema.cinemaName}" class="main-image">
    </div>
    <div class="info-section">
        <div class="cinema-header">
            <h1 class="cinema-name">${cinema.cinemaName}</h1>
            <a class="cinema-email" href="mailto:${cinema.email}">${cinema.email}</a>
<!--            <p class="cinema-email">${cinema.email}</p>-->
        </div>
        <div class="description-box">
            <h2>Description</h2>
            <div class="scrollable-content">      
            <p>${cinema.description}</p>
        </div>
        </div>
    </div>
    `

    cinemaInfo.innerHTML=cinemaDetailHtml;

})