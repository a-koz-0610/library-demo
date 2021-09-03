import helloImg from '../../assets/hello.jpg';

export default function Contact() {
  return `
    <h1>Contact Us</h1>
    <img class='page-image' src='${helloImg}' alt='books'/>
    <article>
        <h4>Contact Us today!</h4>
        <p>(555) 555-5555</p>
    </article>
    `;
}
