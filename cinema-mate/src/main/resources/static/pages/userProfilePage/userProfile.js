document.addEventListener("DOMContentLoaded", async () => {
    const authToken = localStorage.getItem("authToken");

    const profileContent = document.getElementById("profile-content");

    async function fetchUserDetail() {
        try {
            const response = await fetch("http://localhost:8080/api/v1/user", {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${authToken}`,
                    "Content-Type": "application/json",
                },
            });

            if (!response.ok) {
                const error = await response.json();
                if (response.status === 401) {
                    alert("Unauthorized access. Redirecting to login.");
                    window.location.href = "../loginPage/login.html";
                } else {
                    alert(error.message);
                }
                return null;
            }

            return await response.json();
        } catch (e) {
            console.error("Error fetching user details:", e);
            alert("Error while fetching user details");
            return null;
        }
    }

    async function generateHtml() {
        const profile = await fetchUserDetail();
        if (!profile) return;

        document.getElementById("name").value = profile.username || "";
        document.getElementById("email").value = profile.email || "";

        profileContent.innerHTML = `
            <div class="profile-image-container">
                <img id="profileImage" src="${profile.profileImage ? `http://localhost:8080/userProfile/${profile.profileImage}` : "../no-user-profile.png"}" alt="Profile Image">
                <label for="imageUpload" class="camera-icon">
                    <i class="fa-solid fa-pen-to-square"></i>
                </label>
                <input type="file" id="imageUpload" accept="image/*" hidden>
            </div>

            <div id="viewMode" class="profile-info">
                <div class="user-details">
                    <h1 id="displayName">${profile.username}</h1>
                    <a id="displayEmail" href="mailto:${profile.email}" style="color: inherit; text-decoration: none;">
                        ${profile.email}
                    </a>
                </div>
                <div class="action-buttons">
                    <button class="btn btn-edit">
                        <i class="fa-solid fa-pen"></i> Edit Profile
                    </button>
                    <button class="btn btn-password">
                        <i class="fa-solid fa-lock"></i> Change Password
                    </button>
                    <button class="btn btn-logout">
                        <i class="fa-solid fa-right-from-bracket"></i> Logout
                    </button>
                </div>
            </div>`;
    }

    await generateHtml();

    const profileImageInput = document.getElementById("imageUpload");
    if (profileImageInput) {
        profileImageInput.addEventListener("change", async (event) => {
            const file = event.target.files[0];
            if (!file) return;

            const formData = new FormData();
            formData.append("imageFile", file);

            try {
                const response = await fetch("http://localhost:8080/api/v1/user/profile", {
                    method: "PUT",
                    headers: { Authorization: `Bearer ${authToken}` },
                    body: formData,
                });

                if (response.ok) {
                    const userDetail = await fetchUserDetail();
                    if (userDetail) {
                        document.getElementById("profileImage").src = `http://localhost:8080/userProfile/${userDetail.profileImage}`;
                    }
                } else {
                    alert("Failed to update profile image");
                }
            } catch (error) {
                console.error(error);
                alert("An error occurred while updating the profile image.");
            }
        });
    }

    const logoutButton = document.querySelector(".btn-logout");
    const editProfileButton = document.querySelector(".btn-edit");
    const changePasswordButton = document.querySelector(".btn-password");
    const editModeModal = document.getElementById("editMode");
    const passwordModal = document.querySelector(".modal");

    editProfileButton?.addEventListener("click", () => {
        editModeModal.style.display = "flex";
    });

    changePasswordButton?.addEventListener("click", () => {
        passwordModal.style.display = "flex";
    });

    document.querySelector(".edit-btn-cancel")?.addEventListener("click", () => {
        editModeModal.style.display = "none";
    });

    document.querySelector(".btn-cancel")?.addEventListener("click", () => {
        passwordModal.style.display = "none";
    });

    logoutButton?.addEventListener("click", () => {
        localStorage.removeItem("authToken");
        window.location.href = "../loginPage/login.html";
    });

    const profileForm = document.getElementById("profileForm");
    profileForm?.addEventListener("submit", async (event) => {
        event.preventDefault();

        const username = document.getElementById("name").value.trim();
        const email = document.getElementById("email").value.trim();

        try {
            const response = await fetch("http://localhost:8080/api/v1/user", {
                method: "PUT",
                headers: {
                    Authorization: `Bearer ${authToken}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ username, email }),
            });

            if (response.ok) {
                document.getElementById("displayName").textContent = username;
                document.getElementById("displayEmail").textContent = email;
                editModeModal.style.display = "none";
            } else {
                alert("Failed to update profile");
            }
        } catch (error) {
            console.error(error);
            alert("An error occurred while updating the profile.");
        }
    });

    const passwordForm = document.getElementById("passwordForm");
    passwordForm?.addEventListener("submit", async (event) => {
        event.preventDefault();

        const oldPassword = document.getElementById("oldPassword").value.trim();
        const newPassword = document.getElementById("newPassword").value.trim();

        if (newPassword === oldPassword) {
            passwordModal.style.display = "none";
            return;
        }

        try {
            const response = await fetch("http://localhost:8080/api/v1/user/password", {
                method: "PUT",
                headers: {
                    Authorization: `Bearer ${authToken}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ oldPassword, newPassword }),
            });

            if (response.ok) {
                alert("Password updated successfully!");
                passwordModal.style.display = "none";
            } else {
                const error = await response.json();
                alert(error.message);
            }
        } catch (error) {
            console.error(error);
            alert("An error occurred while updating the password.");
        }
    });
});
