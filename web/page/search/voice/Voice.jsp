<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <style>
            /* Define animation for the microphone icon */
            @keyframes pulse {
                0% {
                    transform: scale(1);
                }
                50% {
                    transform: scale(1.1);
                }
                100% {
                    transform: scale(1);
                }
            }
            /* Button styling */
            .voice-button {
                height: 60px;
                width: 60px;
                background-color: #ccc; /* Blue background color */
                color: white; /* White text color */
                border: none; /* No border */
                padding: 10px 10px; /* Padding inside the button */
                border-radius: 50%; /* Rounded corners */
                cursor: pointer; /* Cursor style */
                transition: background-color 0.3s ease; /* Smooth background color transition */
            }

            .voice-button:hover {
                background-color: #d82d8b; /* Darker blue on hover */
            }

            /* Microphone icon styling */
            .voice-button i {
                margin-right: 5px; /* Space between icon and text (adjust as needed) */
                font-size: 1.2em; /* Icon size (adjust as needed) */
            }

            .animate-pulse {
                animation: pulse 1s infinite;
            }

            /* Modal styling */
            .voice-modal {
                display: none; /* Hidden by default */
                position: fixed;
                z-index: 9999;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent background */
                text-align: center;
            }

            .voice-modal .voice-modal-content {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                color:#d82d8b;
                padding: 20px;
                border-radius: 10px;
            }
        </style>
        <script>
            var modal = document.getElementById('modal-voice-talking');
            var modalContent = document.getElementById('voice-modal-content');
            var microphoneIcon = document.getElementById('microphone-icon');
            function startDictation() {
                
                var modal = document.getElementById('modal-voice-talking');
                var modalContent = document.getElementById('voice-modal-content');
                var microphoneIcon = document.getElementById('microphone-icon');

                if (window.hasOwnProperty('webkitSpeechRecognition')) {
                    var recognition = new webkitSpeechRecognition();
                    recognition.continuous = false;
                    recognition.interimResults = false;
                    recognition.lang = "vi-VN";  // Set language to Vietnamese

                    // Show modal when recognition starts
                    modal.style.display = 'block';
                    microphoneIcon.classList.add('animate-pulse');

                    recognition.start();

                    recognition.onresult = function (e) {
                        var transcript = e.results[0][0].transcript.trim();
                        transcript = transcript.replace(/[.,\/#!$%\^&\*;:{}=\-_`~()]/g, '').trim();

                        document.getElementById('searchInput').value = transcript;
                        recognition.stop();
                        modal.style.display = 'none'; // Hide modal when recognition stops
                        microphoneIcon.classList.remove('animate-pulse');
                        document.getElementById('searchForm').submit();
                    };

                    recognition.onerror = function (e) {
                        recognition.stop();
                        modal.style.display = 'none'; // Hide modal on error
                        microphoneIcon.classList.remove('animate-pulse');
                    };

                    // Allow stopping by clicking the microphone icon
                    document.getElementById('stopRecording').addEventListener('click', function () {
                        recognition.stop();
                        modal.style.display = 'none'; // Hide modal on manual stop
                        microphoneIcon.classList.remove('animate-pulse');
                    });
                }
            }
        </script>
    </head>
    <body>

        <form id="searchForm" action="/movie/searchmovie" method="post">
            <input type="hidden" id="searchInput" name="searchInput" placeholder="Speak now">
            <button class="voice-button" type="button"  onclick="startDictation()">  <i id="microphone-icon" class="fas fa-microphone-alt"></i> </button>
        </form>
        <!-- Modal for indicating speech recognition -->
        <div id="modal-voice-talking" class="voice-modal">
            <div class="voice-modal-content">
                <i class="fas fa-microphone-alt animate-pulse" style="font-size: 4em;"></i>
                <p>Listening...</p>
            </div>
        </div>
    </body>
</html>
