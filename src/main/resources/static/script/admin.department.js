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

async function openModal(departmentId=null) {
   const addDepartmentModal =  document.getElementById('addDepartmentModal');
   const addDepartmentForm = addDepartmentModal.querySelector('form');

    if (departmentId !=null) {
        const response = await fetch(`/admin/departments/${departmentId}`).catch(err => console.err(err));
        const department = await response.json();

        const fields = addDepartmentForm.querySelectorAll("input,textarea");
        const map = new Map();
        for (const field of fields) {
            map.set(field.name, field);
        }

        for (let key in department) {
            const field = map.get(key);
            field.value = department[key];
        }
    } else {
        addDepartmentForm.reset();
    }

    addDepartmentModal.classList.remove('hidden');
}

function closeModal() {
    document.getElementById('addDepartmentModal').classList.add('hidden');
}