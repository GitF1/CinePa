
const trailerBtns = document.querySelectorAll('.js-trailer');
const modal = document.querySelector('.js-modal');
const trailerCloses = document.querySelectorAll('.js-trailer-close');
const modalContainer = document.querySelector('.js-modal-container');

function showTrailer() {
    modal.classList.add('open');
}
function closeTrailer() {
    modal.classList.remove('open');

}


for (const trailerBtn of trailerBtns) {
    trailerBtn.addEventListener('click', showTrailer);
}

for (const trailerClose of trailerCloses) {
    trailerClose.addEventListener('click', closeTrailer);
}

modal.addEventListener('click', closeTrailer);
modalContainer.addEventListener('click', (e) => {
    e.stopPropagation();
});
