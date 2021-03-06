package ir.maktab32.java.homeworks.hw9.articles.features.articlemanagement.impl;

import ir.maktab32.java.homeworks.hw9.articles.entities.Article;
import ir.maktab32.java.homeworks.hw9.articles.entities.User;
import ir.maktab32.java.homeworks.hw9.articles.features.articlemanagement.usecase.FindWriterArticlesByWriterUseCase;
import ir.maktab32.java.homeworks.hw9.articles.repositories.ArticleRepository;
import ir.maktab32.java.homeworks.hw9.articles.share.AuthenticationService;
import ir.maktab32.java.homeworks.hw9.articles.utilities.CurrentUserStatus;

import java.util.ArrayList;
import java.util.List;


public class FindWriterArticlesByWriterUseCaseImpl implements FindWriterArticlesByWriterUseCase {
    @Override
    public List<Article> execute() {
        List<Article> result;
        boolean validationResult = validation();
        if (validationResult){
            User currentUser = AuthenticationService.getInstance().getSignedInUser();
            List<Article> allArticles = ArticleRepository.getInstance().findAll();
            result = new ArrayList<>();
            for (Article i : allArticles){
                if (i.getAuthor().getUsername().equals(currentUser.getUsername()))
                    result.add(i);
            }

            if (result.size() != 0)
                System.out.println("\t\t\u2705 Your Articles Loaded Successfully!");
            else {
                System.out.println("\t\t\u26a0 You Have No Articles!");
                result = null;
            }
        }
        else {
            System.out.println("\t\t\u26a0 Loading Articles Failed!");
            result = null;
        }
        return result;
    }

    private boolean validation(){
        boolean result = true;
        if (!CurrentUserStatus.isWriter()){
            System.out.println("\t\u26a0 Please Sign in as A Writer!");
            result = false;
        }
        else if (ArticleRepository.getInstance().findAll().size() == 0){
            System.out.println("\t\u26a0 There is No Article In Database!");
            result = false;
        }

        return result;
    }
}
