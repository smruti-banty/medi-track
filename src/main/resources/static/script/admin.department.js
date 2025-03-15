let previousFile = null; // Store previous file
let previousImageSrc = ""; // Store previous image preview

function previewDepartmentImage(event) {
    const fileInput = event.target;
    const preview = document.getElementById('departmentImagePreview');
    const previewContainer = document.getElementById('imagePreviewContainer');

    // If no file selected, keep the previous image
    if (!fileInput.files.length) {
        fileInput.files = previousFile; // Restore previous file
        preview.src = previousImageSrc; // Restore previous image
        return;
    }

    // Store the new file and preview
    previousFile = fileInput.files;
    const reader = new FileReader();
    reader.onload = function () {
        previousImageSrc = reader.result; // Store the new image source
        preview.src = reader.result;
        previewContainer.classList.remove('hidden'); // Show preview & remove button
    };
    reader.readAsDataURL(fileInput.files[0]);
}

function removeImage() {
    const fileInput = document.getElementById('departmentImageInput');
    const preview = document.getElementById('departmentImagePreview');
    const previewContainer = document.getElementById('imagePreviewContainer');

    // Reset file input & hide preview
    fileInput.value = "";
    previousFile = null;
    previousImageSrc = "";
    previewContainer.classList.add('hidden'); // Hide preview & remove button
}

function openModal() {
    document.getElementById('addDepartmentModal').classList.remove('hidden');
}

function closeModal() {
    document.getElementById('addDepartmentModal').classList.add('hidden');
}