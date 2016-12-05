package controller;

import entity.User;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "userController")
@ViewScoped
public class UserController extends AbstractController<User> {

    public UserController() {
        // Inform the Abstract parent controller of the concrete User Entity
        super(User.class);
    }

}
