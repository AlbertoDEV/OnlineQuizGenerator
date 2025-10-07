document.addEventListener('DOMContentLoaded', function() {
    const menuToggle = document.getElementById('menu-toggle');
    const languageMenu = document.getElementById('language-menu');

    if (menuToggle) {
        menuToggle.addEventListener('click', function() {
            languageMenu.classList.toggle('show');
        });
    }

    // Close the dropdown if the user clicks outside of it
    window.onclick = function(event) {
        if (!event.target.matches('.menu-toggle') && !event.target.closest('.menu-toggle')) {
            if (languageMenu.classList.contains('show')) {
                languageMenu.classList.remove('show');
            }
        }
    }
});