package by.sunserg.grandcapital.controller;

import by.sunserg.grandcapital.service.dto.response.UserSearchResponseDto;
import by.sunserg.grandcapital.service.iservice.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name = "Контроллер пользователей", description = "Контроллер для работы с пользователями")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final IUserService userService;

    @Operation(summary = "Операция поиска пользователей", description = "Операция поиска пользователей по параметрам (дате рождения, номеру телефона, email, имени) с постраничным выводом")
    @GetMapping("/search")
    public ResponseEntity<Page<UserSearchResponseDto>> search(@RequestParam(value = "dateOfBirth", required = false)
                                                              @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateOfBirth,
                                                              @RequestParam(value = "phone", required = false) String phone,
                                                              @RequestParam(value = "name", required = false) String name,
                                                              @RequestParam(value = "email", required = false) String email,
                                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<UserSearchResponseDto> result = userService.searchUsers(dateOfBirth, phone, name, email, page, size);

        return ResponseEntity.ok(result);
    }
}
