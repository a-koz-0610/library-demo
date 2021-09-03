export default function HashTags(hashTags) {
  return `
    <h1>Saved HashTags</h1>
    <ul class='hahTags-list'>
    ${hashTags
      .map((hashTag) => {
        return `
        <li class='hashTags-list__name'>${hashTag.name}
            <button class='hashtags-list__remove'>x</button>
            <input class='hashtags-list__id' type='hidden' id=${hashTag.id} value=${hashTag.id} />
        </li>
        `;
      })
      .join('')}
    </ul>

    <section class='add-hashtag'>
        <input class='add-hashtag__name' type='text' placeholder='Add a HashTag to Save' />
        <button class='add-hashtag__submit'>Save</button>
    </section>


    `;
}
