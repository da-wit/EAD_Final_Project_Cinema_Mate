// document.addEventListener('DOMContentLoaded', () => {
//     const scriptTag = document.getElementById('navbar-script');
//     const navbarPath = scriptTag.getAttribute('data-navbar-path');
//     console.log(navbarPath)
//
//     if (!navbarPath) {
//         console.error('Navbar path not provided.');
//         return;
//     }
//
//     const navbarPlaceholder = document.getElementById('navbar-placeholder');
//     if (!navbarPlaceholder) {
//         console.error('Navbar placeholder not found.');
//         return;
//     }
//
//     fetch(navbarPath)
//         .then(response => response.text())
//         .then(data => {
//             navbarPlaceholder.innerHTML = data;
//
//             const navLinks = document.querySelectorAll('.nav-link');
//             navLinks.forEach(link => {
//                 link.addEventListener('click', () => {
//                     navLinks.forEach(eachLink => eachLink.classList.remove('active'));
//                     link.classList.add('active');
//                 });
//             });
//         })
//         .catch(error => console.error('Error loading navbar:', error));
// });
