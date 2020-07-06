//package portal.education.Monolit.config;
//
//import portal.education.Monolit.controller.AdminController;
//import portal.education.Monolit.data.model.Category;
//import portal.education.Monolit.data.model.Tag;
//import portal.education.Monolit.data.model.article.Article;
//import portal.education.Monolit.data.model.article.ArticleStatus;
//import portal.education.Monolit.data.model.comment.Comment;
//import portal.education.Monolit.data.model.person.Author;
//import portal.education.Monolit.data.model.person.Role;
//import portal.education.Monolit.data.model.person.User;
//import portal.education.Monolit.data.repos.CategoryRepository;
//import portal.education.Monolit.data.repos.article.ArticleRepository;
//import portal.education.Monolit.data.repos.article.TagRepository;
//import portal.education.Monolit.data.repos.comment.CommentRepository;
//import portal.education.Monolit.data.repos.person.AuthorRepository;
//import portal.education.Monolit.data.repos.person.RoleRepository;
//import portal.education.Monolit.data.repos.person.UserRepository;
//import portal.education.Monolit.service.FileStorageService;
//import portal.education.Monolit.service.article.ArticleCrudService;
//import portal.education.Monolit.service.person.AccountConfirmationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.persistence.EntityManager;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.*;
//
//
//@Component
//public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
//
//    private boolean alreadySetup = false;
//
//    @Autowired
//    private UserRepository<User> userDao;
//
//    @Autowired
//    private AuthorRepository authorDao;
//
//    @Autowired
//    private RoleRepository roleDao;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private CategoryRepository categoryDao;
//
//    @Autowired
//    private TagRepository tagDao;
//
//    @Autowired
//    private ArticleCrudService articleCrud;
//
//    @Autowired
//    private AdminController adminController;
//
//    @Autowired
//    private ArticleRepository articleDao;
//
//    @Autowired
//    EntityManager entityManager;
//
//    @Autowired
//    FileStorageService fileStorageService;
//
//    @Autowired
//    CommentRepository commentRepository;
//
//    @Autowired
//    AccountConfirmationService accountConfirmationService;
//
//    List<String> categories = List.of("Спорт", "Политика", "Кино", "Религия", "Музыка", "Половое воспитание", "Привет", "Денис", "Никита", "Миша", "Еда", "Медицина");
//
//    List<String> tags = List.of("За себя и за Сашку", "Ещё повезёт", "С++", "Java", "С#", "Java Script", "Perl", "Sun", "Eva", "Adam");
//
//    @Override
//    @Transactional
//    public void onApplicationEvent(final ContextRefreshedEvent event) {
//
//
//        if (alreadySetup) {
//            return;
//        }
//
//        // == create initial roles
//        createRoleIfNotFound("ROLE_ADMIN");
//        createRoleIfNotFound("ROLE_USER");
//        createRoleIfNotFound("ROLE_AUTHOR");
//
//
//        // == create initial user
//        User comus = createUserIfNotFound("efremov20081@gmail.com",
//                "UserTest",
//                "123",
//                Set.of((roleDao.findByName("ROLE_USER"))));
//        var o =accountConfirmationService.findByUserOrCreate((short)3,comus,"Email",comus.getAccountIdentification());
//        o.setConfirm(true);
//
//
//        createUserIfNotFound(null,
//                "NotRegUserTest",
//                "123",
//                Set.of((roleDao.findByName("ROLE_USER")))).setAccountConfirmed(false);
//
//        createUserIfNotFound("admin@test.com",
//                "AdminTest",
//                "123",
//                Set.of(roleDao.findByName("ROLE_ADMIN")));
//
//        Author author = createAuthorIfNotFound("writer@test.com",
//                "WriterTest",
//                "123",
//                Set.of(roleDao.findByName("ROLE_AUTHOR")));
//
//        User users = createUserIfNotFound("super@test.com",
//                "SuperTest", "123",
//                Set.of(roleDao.findByName("ROLE_AUTHOR"), roleDao.findByName("ROLE_ADMIN"), roleDao.findByName("ROLE_USER")));
//
//
//        List<Category> categoryObjs = new ArrayList<>();
//        for (String categ : categories) {
//            categoryObjs.add(createCategoryIfNotFound(categ, users));
//        }
//
//
//        List<Tag> tagObjs = new ArrayList<>();
//        for (String tag : tags) {
//            tagObjs.add(createTagIfNotFound(tag, users));
//        }
//
//        UUID idart = fileStorageService.storeArticleFile(loadFile());
//
//        StringBuilder stringBuilder = new StringBuilder(bodyPart1);
//        stringBuilder.append(idart);
//        stringBuilder.append(bodyPart2);
//
//        final Random random = new Random();
//
//        for (int i = 0; i < 50; i++) {
//
//            Article article = createArticleIfNotFound(author, String.valueOf(i), getRandomCategory(categoryObjs, random), stringBuilder.toString(), getRandomSetTags(tagObjs, random), random);
//            createComments(article, comus);
//            createComments(article, users);
//            createComments(article, author);
//        }
//        for (int i = 50; i < 100; i++) {
//            createArticleIfNotFound(author, String.valueOf(i), getRandomCategory(categoryObjs, random), stringBuilder.toString(), getRandomSetTags(tagObjs, random), random);
//        }
//
//        alreadySetup = true;
//    }
//
//    Category getRandomCategory(List<Category> categoryObjs, Random random) {
//        return categoryObjs.get(random.nextInt(categoryObjs.size()));
//    }
//
//    Set<Tag> getRandomSetTags(List<Tag> tagObjs, Random random) {
//        Integer range = random.nextInt(tagObjs.size());
//        var tags = new HashSet<Tag>();
//        for (Integer i = 0; i < range; i++) {
//            tags.add(tagObjs.get(random.nextInt(range)));
//        }
//        return tags;
//    }
//
//    String bodyPart1 = "<p>Page content</p>\n" +
//            "<h2>Title 1</h2><p>Change the content of this editor, then save it on the server.</p><ul><li>You will be asked whether you want to leave the page if an image is being uploaded" +
//            " or the data has not been saved successfully yet. You can test that by dropping a big image into the editor or changing the “HTTP server lag” to a high value (e.g. 9000ms) and " +
//            "clicking the “Save” button. These actions will make the editor “busy” for a longer time — try leaving the page then.</li><li>The button changes to “Saving…” when the data is being " +
//            "sent to the server or there are any other pending actions (e.g. an image being uploaded).</li></ul><figure class=\"image\"><img" +
//            " src=\"/api/free/user/downloadArticleFile/";
//    String bodyPart2 = "\" width=100%\"></figure>";
//
//    private Article createArticleIfNotFound(Author author, String title, Category category, String body, Set<Tag> tags, Random random) {
//        Article article = articleCrud.put(new Article(author, title, body, category, tags));
//        adminController.patchArticleStatus(article.getId(), ArticleStatus.values()[random.nextInt(ArticleStatus.values().length)]);
//        return article;
//    }
//
//
//    @Transactional
//    private final Role createRoleIfNotFound(final String name) {
//        Role role = roleDao.findByName(name);
//        if (role == null) {
//            role = new Role(name);
//            role = roleDao.save(role);
//        }
//        return role;
//    }
//
//
//    @Transactional
//    private final Tag createTagIfNotFound(final String name, final User author) {
//        Tag tag = tagDao.findByName(name).orElse(null);
//        if (tag == null) {
//            tag = new Tag(name, author);
//            tag = tagDao.save(tag);
//        }
//        return tag;
//    }
//
//
//    @Transactional
//    private final Category createCategoryIfNotFound(final String name, final User author) {
//        Category category = categoryDao.findByName(name).orElse(null);
//        if (category == null) {
//            category = new Category(name, author);
//            category = categoryDao.save(category);
//        }
//        return category;
//    }
//
//    @Transactional
//    private final User createUserIfNotFound(final String email, final String nickname, final String password, final Set<Role> roles) {
//        User user = userDao.findByNickname(nickname).orElse(null);
//        if (user == null) {
//            user = new User();
//            user.setNickname(nickname);
//            user.setPassword(passwordEncoder.encode(password));
//            user.setAccountIdentification(email);
//            user.setAccountEnabled(true);
//            user.setAccountConfirmed(true);
//            user.setOnline(true);
//        }
//        user.setRoles(roles);
//        user = userDao.save(user);
//        return user;
//    }
//
//    @Transactional
//    private final Author createAuthorIfNotFound(final String email, final String nickname, final String password, final Set<Role> roles) {
//        Author author = authorDao.findByAccountIdentification(email).orElse(null);
//        if (author == null) {
//            author = new Author();
//            author.setNickname(nickname);
//            author.setPassword(passwordEncoder.encode(password));
//            author.setAccountIdentification(email);
//            author.setAccountEnabled(true);
//            author.setAccountConfirmed(true);
//            author.setOnline(true);
//        }
//        author.setRoles(roles);
//        author = userDao.save(author);
//        return author;
//    }
//
//    MultipartFile loadFile() {
//        Path path = Paths.get("src/main/resources/image.png");
//        String name = "image.png";
//        String originalFileName = "image.png";
//        String contentType = "image/png";
//        byte[] content = null;
//        try {
//            content = Files.readAllBytes(path);
//        } catch (final IOException e) {
//
//        }
//        return new MockMultipartFile(name,
//                originalFileName, contentType, content);
//    }
//
//
//    void createComments(Article article, User user) {
//        final Random random = new Random();
//        Integer range = random.nextInt(20);
//        for (int i = 0; i < range; i++) {
//            commentRepository.save(new Comment(article, user, String.valueOf(i)));
//        }
//    }
//
//}