function previewImage(event) {
    const reader = new FileReader();
    reader.onload = function () {
        document.getElementById('profilePreview').src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);

    const {id, token} = event.target.dataset;

    // Send image to the backend
    uploadProfilePicture(event.target.files[0], id, token);
}

function uploadProfilePicture(file, id, token) {
    let formData = new FormData();
    formData.append("file", file);
    formData.append("uploadId", id);
    formData.append("_csrf", token);

    fetch('/file/update-profile-picture', {
        method: 'POST',
        body: formData
    })
        .then(response => {
        location.reload();
    }) .catch(error => console.error("Error uploading image:", error));
}
