// Login form handling
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('login-form');
    
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            
            // Here you would normally send a request to your Spring Boot backend
            // For demo purposes, we'll just redirect to the dashboard
            if (username && password) {
                // Example API call to your Spring Boot backend
                /*
                fetch('/api/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        username: username,
                        password: password
                    })
                })
                .then(response => {
                    if (response.ok) {
                        window.location.href = '/dashboard.html';
                    } else {
                        alert('Invalid credentials');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
                */
                
                // For demo, just redirect
                window.location.href = 'dashboard.html';
            }
        });
    }
    
    // Dashboard functionality
    const newNoteBtn = document.querySelector('.new-note-btn');
    if (newNoteBtn) {
        newNoteBtn.addEventListener('click', function() {
            // Here you would implement note creation logic
            alert('Create new note feature would be implemented here');
            
            // Example API call to your Spring Boot backend
            /*
            fetch('/api/notes', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    title: 'New Note',
                    content: ''
                })
            })
            .then(response => response.json())
            .then(data => {
                console.log('Note created:', data);
                // Add note to the list or redirect to edit page
            })
            .catch(error => {
                console.error('Error:', error);
            });
            */
        });
    }
});