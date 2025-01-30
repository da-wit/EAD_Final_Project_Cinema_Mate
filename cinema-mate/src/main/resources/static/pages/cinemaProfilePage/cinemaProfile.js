document.addEventListener("DOMContentLoaded", async () => {

    const authToken = localStorage.getItem('authToken')


    const profileContent = document.getElementById('profile-content')

    async function fetchUserDetail(){
        try{
            const response = await fetch('http://localhost:8080/api/v1/cinema',{
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
                    throw new Error(error.message);
                }
                return;
            }

            return await response.json();
        }catch (e){
            console.error('Error fetching user detail:', e);
            alert(e.message)
        }

    }



    async  function generateHtml (){
        const profile = await fetchUserDetail();
        const nameField = document.getElementById('name');
        const emailField = document.getElementById('email');
        const descriptionField = document.getElementById('description')

        // Set the values from the user data
        nameField.value = profile.cinemaName || ''; // Assuming `username` holds the name
        emailField.value = profile.email || '';
        descriptionField.value = profile.description || '';


        return profileContent.innerHTML = `
    <div class="profile-image-container">
            <img id="profileImage" src="${profile.imagePath ? `http://localhost:8080/cinemaProfile/${profile.imagePath}` : '../no-cinema-profile.png'}" alt="Profile Image">
            <label for="imageUpload" class="camera-icon">
                <i class="fa-solid fa-pen-to-square"></i>
            </label>
            <input type="file" id="imageUpload" accept="image/*" hidden>
        </div>

        <div id="viewMode" class="profile-info">
            <div class="cinema-details">
                <h1 id="displayName">${profile.cinemaName.charAt(0).toUpperCase()  + profile.cinemaName.slice(1).toLowerCase()}</h1>
                <p id="displayEmail">
        <a href="mailto:${profile.email}" style="color: inherit; text-decoration: none;">
        ${profile.email}
        </a>
        </p>

                <p id="displayDescription">${profile.description}</p>
            </div>
            <div class="action-buttons">
                <button class="btn btn-edit">
                    <i class="fa-solid fa-pen"></i>
                    Edit Profile
                </button>
                <button  class="btn btn-password">
                    <i class="fa-solid fa-lock"></i>
                    Change Password
                </button>
                <button class="btn btn-logout">
                    <i class="fa-solid fa-right-from-bracket"></i>
                    Logout
                </button>
            </div>
        </div>`;

    }
    await generateHtml();


    const profileImageInput = document.getElementById("imageUpload");
    profileImageInput.addEventListener("change", async (event) => {
        const file = event.target.files[0];
        if (file) {
            console.log(file)
            const formData = new FormData();
            formData.append("imageFile", file);

            try {
                const response = await fetch("http://localhost:8080/api/v1/cinema/profile", {
                    method: "PUT",
                    headers: {
                        'Authorization': `Bearer ${authToken}`,
                    },
                    body: formData,
                });

                if (response.ok) {
                    // const data = await response.json();
                    const cinemaDetail = await  fetchUserDetail();
                    document.getElementById("profileImage").src = `http://localhost:8080/cinemaProfile/${cinemaDetail.imagePath}`;
                    // alert("Profile image updated successfully!");
                } else {
                     alert("Failed to update profile image");
                }
            } catch (error) {
                console.error(error);
                alert("An error occurred while updating the profile image.");
            }
        }
    });
    const logoutButton = document.querySelector(".btn-logout");

    const editProfileButton = document.querySelector(".btn-edit");
    const editModeModal = document.getElementById("editMode");
    const changePasswordButton = document.querySelector(".btn-password")
    const passwordModal =document.querySelector(".modal");

    editProfileButton.addEventListener("click", () => {
        editModeModal.style.display='flex';
    });
    changePasswordButton.addEventListener("click",()=>{
        passwordModal.style.display ='flex';
    })


    const closeButton = editModeModal.querySelector(".edit-btn-cancel");
    const closePasswordButton = passwordModal.querySelector(".btn-cancel");
    closeButton.addEventListener("click", () => {
        editModeModal.style.display='none';
    });
    closePasswordButton.addEventListener("click", () => {
        passwordModal.style.display='none';
    });

    logoutButton.addEventListener("click",()=>{
        localStorage.removeItem('authToken');
        window.location.href = "../loginPage/login.html"
    })

    const profileForm = document.getElementById('profileForm');

    profileForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const cinemaName = document.getElementById("name").value.trim();
        const email = document.getElementById("email").value.trim();
        const description = document.getElementById('description').value.trim();
        const data = {
            cinemaName:cinemaName,
            email:email,
            description:description

        }

        try {
            const response = await fetch("http://localhost:8080/api/v1/cinema", {
                method: "PUT",
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                document.getElementById("displayName").textContent = cinemaName;
                document.getElementById("displayEmail").textContent = email;
                document.getElementById("displayDescription").textContent = description;
                editModeModal.style.display='none';
            } else {
                alert("Failed to update profile");
            }
        } catch (error) {
            console.error(error);
            alert("An error occurred while updating the profile.");
        }
    });

    const  passwordForm = document.getElementById('passwordForm');
    passwordForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const oldPassword = document.getElementById("oldPassword").value.trim();
        const newPassword = document.getElementById("newPassword").value.trim();

        if (newPassword === oldPassword) {
            passwordModal.style.display='none';
            document.getElementById("oldPassword").value = '';
            document.getElementById("newPassword").value = '';
            return;
        }

        const  data ={
            oldPassword:oldPassword,
            newPassword:newPassword
        }

        try {
            const response = await fetch("http://localhost:8080/api/v1/cinema/password", {
                method: "PUT",
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                alert("Password updated successfully!");
                passwordModal.style.display='none';
            } else {
                const error = await response.json()
                alert(error.message);
            }
            document.getElementById("oldPassword").value = '';
            document.getElementById("newPassword").value = '';

        } catch (error) {
            console.error(error);
            alert("An error occurred while updating the password.");
        }
    });

});
