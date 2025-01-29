const loginForm = document.getElementById('loginForm');
const errorDiv = document.getElementById('error');



loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();


    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const  loginData = {
        username:username,
        password:password
    }

    try {
        const response = await fetch('/api/v1/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData),
        });

        if (response.ok) {
            const data = await response.json();

            localStorage.setItem('authToken', data.token);
            const  payload = JSON.parse(atob(data.token.split('.')[1]))
            const role = payload.role;
            // alert(role === 'USER')
            if(role === 'USER'){
                window.location.href = '../userHomePage/userHome.html'
            }else if(role === 'CINEMA') {
            window.location.href = '../cinemaHomePage/cinemaHome.html';
            }else{
                window.location.href = '../registrationPage/register.html';
            }

        } else {
            const error = await response.json();
            errorDiv.textContent = error.message || 'Invalid credentials';

            setTimeout(() => {
                errorDiv.textContent = "";
            },3000)
        }
    } catch (error) {
        console.error('Error during login:', error);
        errorDiv.textContent = 'Something went wrong. Please try again.';
    }
});
