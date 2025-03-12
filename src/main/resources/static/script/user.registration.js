function toggleFields() {
    let userRole = document.getElementById("userRole").value;
    let doctorFields = document.getElementById("doctorFields");
    doctorFields.classList.toggle("hidden", userRole !== "DOCTOR");
}

function previewImage(event) {
    let file = event.target.files[0];
    let image = document.getElementById("imagePreview");
    let dropText = document.getElementById("uploadText");
    let dropArea = document.getElementById("dropArea");

    if (file) {
        let reader = new FileReader();
        reader.onload = function () {
            image.src = reader.result;
            image.classList.remove("hidden");
            dropText.classList.add("hidden"); // Hide text
        };
        reader.readAsDataURL(file);
    }
}

function handleDrop(event) {
    event.preventDefault();
    let fileInput = document.getElementById("profilePicture");
    fileInput.files = event.dataTransfer.files;
    previewImage({ target: fileInput });
}

function handleDragOver(event) {
    event.preventDefault();
    document.getElementById("dropArea").classList.add("border-blue-500");
}

function handleDragLeave(event) {
    document.getElementById("dropArea").classList.remove("border-blue-500");
}