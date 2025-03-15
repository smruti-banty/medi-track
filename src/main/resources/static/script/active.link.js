document.addEventListener("DOMContentLoaded", function () {
    // Get current page URL
    const currentPage = window.location.pathname;

    // Get all sidebar links
    const sidebarLinks = document.querySelectorAll("aside a");

    sidebarLinks.forEach(link => {
        // Compare link's href with current page URL
        if (link.getAttribute("href") === currentPage) {
            link.classList.add("active-link"); // Add active class
        }
    });
});