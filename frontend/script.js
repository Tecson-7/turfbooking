const BASE_URL = "http://localhost:8080/api";

// Fetch and display turfs
async function fetchTurfs() {
    try {
        const response = await fetch("http://localhost:8080/turfs");
        const turfs = await response.json();
        const turfList = document.getElementById("turfList");
        turfList.innerHTML = "";
        turfs.forEach(turf => {
            const li = document.createElement("li");
            li.textContent = `${turf.name} - ${turf.location} - ₹${turf.pricePerHour}`;
            turfList.appendChild(li);
        });
    } catch (error) {
        console.error("Error fetching turfs:", error);
    }
}

// Fetch and display all bookings
async function fetchBookings() {
    try {
        const response = await fetch(`${BASE_URL}/bookings/all`);
        const bookings = await response.json();
        const bookingList = document.getElementById("bookingList");
        bookingList.innerHTML = "";
        bookings.forEach(b => {
            const div = document.createElement("div");
            div.className = "booking-card";
            div.innerHTML = `
                <p>ID: ${b.id}</p>
                <p>Turf ID: ${b.turfId}</p>
                <p>Date: ${b.bookingDate}</p>
                <p>Time: ${b.startTime} - ${b.endTime}</p>
                <p>Status: ${b.status}</p>
                <button onclick="deleteBooking(${b.id})">Cancel</button>
            `;
            bookingList.appendChild(div);
        });
    } catch (error) {
        console.error("Error fetching bookings:", error);
    }
}

// Create a booking
document.getElementById("bookingForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const turfId = document.getElementById("turfId").value;
    const bookingDate = document.getElementById("bookingDate").value;
    const startTime = document.getElementById("startTime").value;
    const endTime = document.getElementById("endTime").value;

    const booking = { turfId: Number(turfId), bookingDate, startTime, endTime };

    try {
        const response = await fetch(`${BASE_URL}/bookings`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(booking)
        });

        if (!response.ok) {
            const error = await response.json();
            alert(`Error: ${error.message}`);
            return;
        }

        alert("Booking successful!");
        fetchBookings();
    } catch (error) {
        console.error("Error creating booking:", error);
    }
});

// Delete a booking
async function deleteBooking(id) {
    if (!confirm("Are you sure you want to cancel this booking?")) return;
    try {
        const response = await fetch(`${BASE_URL}/bookings/${id}`, { method: "DELETE" });
        if (!response.ok) {
            const error = await response.json();
            alert(`Error: ${error.message}`);
            return;
        }
        alert("Booking canceled!");
        fetchBookings();
    } catch (error) {
        console.error("Error deleting booking:", error);
    }
}

// Initial load
fetchTurfs();
fetchBookings();
