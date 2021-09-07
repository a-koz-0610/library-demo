export default function Book(book) {
  return `
    <h1>${book.title}</h1>
    <h2>${book.description}</h2>
    <h3>Authors...</h3>


    <h3>HashTags...</h3>
    <ul>
    ${book.hashTags
      .map((hashTag) => {
        return `
          <li>
              <span>${hashTag.name}</span>

          </li>
          `;
      })
      .join('')}
    </ul>


    <section class='add-hashtag'>
      <input class='add-hashtag__name' type='text' placeholder='Attach a HashTag' />
      <button class='add-hashtag__bookSubmit'>Submit</button>
      <input type='hidden' id='bookId' value=${book.id} />
    </section>
    `;
}
