function openModal(doctorId) {
    fetchDoctorDetails(doctorId);
    document.getElementById('doctorModal').classList.remove('hidden');
}

function closeModal() {
    document.getElementById('doctorModal').classList.add('hidden');
}

async function fetchDoctorDetails(doctorId) {
    try {
        const reponse = await fetch(`/client/doctors/${doctorId}`).catch(err => alert(err));
        const doctor = await reponse.json();

        document.getElementById("doctorImage").src=`/file/images/${doctor.doctorId}`;
        document.getElementById("doctorName").textContent = doctor.doctorName;
        document.getElementById("doctorSpecialization").textContent = doctor.doctorSpecialization;
        document.getElementById("doctorEmail").textContent = doctor.doctorEmail;
        document.getElementById("doctorExperience").textContent = `${doctor.doctorExperience} Years`;
        document.getElementById("doctorRate").textContent = `₹${doctor.doctorRate}/hr`;
        document.getElementById("doctorLicense").textContent = doctor.doctorLicense;
        document.getElementById("doctorAddress").textContent = doctor.doctorAddress;
    } catch (e) {
        console.error(e);
    }
}

function openAppointmentModal(doctorId) {
    document.getElementById("doctorIdField").value=doctorId;
    document.getElementById("appointmentModal").classList.remove("hidden");
}

function closeAppointmentModal() {
    document.getElementById("appointmentModal").classList.add("hidden");
}