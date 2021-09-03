import Header from './components/Header';
import Footer from './components/Footer';
import Contact from './components/Contact';
import Home from './components/Home';
import crud from './crud/crud';
import HashTags from './components/HashTags';

buildPage();

function buildPage() {
  header();
  footer();
  navContact();
  navHome();
  navHashTags();
}

function header() {
  const headerElem = document.querySelector('.header');
  headerElem.innerHTML = Header();
}

function footer() {
  const footerElem = document.querySelector('.footer');
  footerElem.innerHTML = Footer();
}

function navContact() {
  const contactElem = document.querySelector('.nav-list__contact');
  contactElem.addEventListener('click', () => {
    const app = document.querySelector('#app');
    app.innerHTML = Contact();
  });
}

function navHome() {
  const homeElem = document.querySelector('.nav-list__home');
  homeElem.addEventListener('click', () => {
    const app = document.querySelector('#app');
    app.innerHTML = Home();
  });
}

function navHashTags() {
  const app = document.querySelector('#app');
  const hashTagElem = document.querySelector('.nav-list__hashtags');
  hashTagElem.addEventListener('click', () => {
    crud.getRequest('http://localhost:8080/api/hashtags', (hashTags) => {
      app.innerHTML = HashTags(hashTags);
    });
  });

  app.addEventListener('click', (event) => {
    if (event.target.classList.contains('add-hashtag__submit')) {
      const hashTagName =
        event.target.parentElement.querySelector('.add-hashtag__name').value;
      crud.postRequest(
        'http://localhost:8080/api/add-hashtag',
        {
          hashTagName: hashTagName,
        },
        (hashTags) => (app.innerHTML = HashTags(hashTags))
      );
    }
  });

  app.addEventListener('click', (event) => {
    console.log(event.target);
    if (event.target.classList.contains('hashtags-list__remove')) {
      const hashTagId =
        event.target.parentElement.querySelector('.hashtags-list__id').value;
      crud.deleteRequest(
        `http://localhost:8080/api/hashtags/${hashTagId}/delete-hashtag`,
        (hashTags) => (app.innerHTML = HashTags(hashTags))
      );
    }
  });
}
