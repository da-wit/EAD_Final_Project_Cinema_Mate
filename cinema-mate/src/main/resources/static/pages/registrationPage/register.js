document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('registrationForm');
    // const tabBtns = document.querySelectorAll('.tab-btn');
    const userTab = document.querySelector(".user")
    const cinemaTab = document.querySelector(".cinema")
    const nameInput = document.getElementById('nameInput');
    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword')
    const descriptionInput = document.getElementById('descriptionInput');
    const errorDiv = document.getElementById('error')
    let type = 'user';
    let data;



    userTab.addEventListener('click',()=>{
        cinemaTab.classList.remove('active')
        userTab.classList.add('active');
        nameInput.placeholder = "Username";
        descriptionInput.style.display = 'none';
        descriptionInput.required = false;
        type = 'user'
    })
    cinemaTab.addEventListener('click',()=>{
        userTab.classList.remove('active');
        cinemaTab.classList.add('active');
        nameInput.placeholder = "Cinema Name"
        descriptionInput.style.display = 'block';
        descriptionInput.required = true;
        type = 'cinema'
    })



    // Form submission
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        console.log(type)

        if(password.value !== confirmPassword.value){
            alert("password does not match")
            return;
        }

        try{
            if(type === 'user'){
                data = {
                    username:nameInput.value,
                    email:email.value,
                    password:password.value,
                    confirmPassword:confirmPassword.value
                }
            }else if(type === 'cinema'){
                data={
                    cinemaName:nameInput.value,
                    email:email.value,
                    password:password.value,
                    confirmPassword:confirmPassword.value,
                    description:descriptionInput.value
                }
            }
            const response = await fetch(`http://localhost:8080/api/v1/auth/${type}`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(data),
            });

            if (response.ok) {
            alert('Registration successful!');
            window.location.href = '../loginPage/login.html';
            } else {
                const error = await response.json();
                errorDiv.textContent = error.message || 'Registration failed.';
            }



        }catch (error) {
            console.error('Error during login:', error);
            errorDiv.textContent = 'Something went wrong. Please try again.';
        }
    });
});
