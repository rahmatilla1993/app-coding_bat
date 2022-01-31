package com.example.appcoding_bat.service;

import com.example.appcoding_bat.entity.Answer;
import com.example.appcoding_bat.entity.Task;
import com.example.appcoding_bat.entity.User;
import com.example.appcoding_bat.enums.ElementNotFound;
import com.example.appcoding_bat.models.Result;
import com.example.appcoding_bat.models.UserDTO;
import com.example.appcoding_bat.repository.AnswerRepository;
import com.example.appcoding_bat.repository.TaskRepository;
import com.example.appcoding_bat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    ElementNotFound messageUser = ElementNotFound.USER;
    ElementNotFound messageTask = ElementNotFound.TASK;
    ElementNotFound messageAnswer = ElementNotFound.ANSWER;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    AnswerRepository answerRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Result getUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(user -> new Result(true, user)).orElseGet(() -> new Result(messageUser.getMessage(), false));
    }

    private Result addingUser(UserDTO userDTO, boolean create, boolean edit, Integer id) {
        User user = new User();
        if (create && userRepository.existsByUserName(userDTO.getUserName()) ||
                edit && userRepository.existsByIdIsNotAndUserName(id, userDTO.getUserName())) {
            return new Result("Bunday user bor", false);
        }
        if (create && userRepository.existsByEmail(userDTO.getEmail()) ||
                edit && userRepository.existsByIdIsNotAndEmail(id, userDTO.getEmail())) {
            return new Result("Bunday email bor", false);
        }
        if (create && userRepository.existsByPassword(userDTO.getPassword()) ||
                edit && userRepository.existsByIdIsNotAndPassword(id, userDTO.getPassword())) {
            return new Result("Bunday parol bor", false);
        }
        Set<Task> tasks = new HashSet<>();
        for (Integer tasks_id : userDTO.getTasks_ids()) {
            Optional<Task> optionalTask = taskRepository.findById(tasks_id);
            if (optionalTask.isPresent()) {
                Task task = optionalTask.get();
                tasks.add(task);
            } else
                return new Result(messageTask.getMessage(), false);
        }
        Optional<Answer> optionalAnswer = answerRepository.findById(userDTO.getAnswer_id());
        if (!optionalAnswer.isPresent()) {
            return new Result(messageAnswer.getMessage(), false);
        }
        Answer answer = optionalAnswer.get();
        if (create && userRepository.existsByAnswer_Id(userDTO.getAnswer_id()) ||
                edit && userRepository.existsByIdIsNotAndAnswer_Id(id, userDTO.getAnswer_id())) {
            return new Result("Bu javob boshqa userga tegishli", false);
        }
        user.setUserName(userDTO.getUserName());
        user.setAnswer(answer);
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setTasks(tasks);
        return new Result(true, user);
    }

    public Result addUser(UserDTO userDTO) {
        Result result = addingUser(userDTO, true, false, null);
        if (result.isSuccess()) {
            User user = (User) result.getObject();
            userRepository.save(user);
            return new Result("User qo'shildi",true);
        }
        return result;
    }

    public Result editUserById(Integer id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User editUser = optionalUser.get();
            Result result = addingUser(userDTO, false, true, id);
            if (result.isSuccess()) {
                User user = (User) result.getObject();
                editUser.setTasks(user.getTasks());
                editUser.setUserName(user.getUserName());
                editUser.setEmail(user.getEmail());
                editUser.setPassword(user.getPassword());
                editUser.setAnswer(user.getAnswer());
                userRepository.save(editUser);
                return new Result("User tahrirlandi", true);
            }
            return result;
        }
        return new Result(messageUser.getMessage(), false);
    }

    public Result deleteUserById(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            userRepository.delete(optionalUser.get());
            return new Result("User o'chirildi",true);
        }
        return new Result(messageUser.getMessage(), false);
    }
}
