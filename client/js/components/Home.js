import bookImg from '../../assets/books.jpg';

export default function Home() {
  return `
    <h1>Welcome to WCCI Library</h1>
    <img class='page-image' src=${bookImg} alt='books' />
    `;
}
