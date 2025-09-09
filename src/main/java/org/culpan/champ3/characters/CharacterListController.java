package org.culpan.champ3.characters;

import org.culpan.champ3.users.User;
import org.culpan.champ3.users.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/characters")
public class CharacterListController {
    private static final int PAGE_SIZE = 10;

    private final ChampCharacterRepository chars;
    private final UserRepository users;

    public CharacterListController(ChampCharacterRepository chars, UserRepository users) {
        this.chars = chars;
        this.users = users;
    }

    private User currentUser(Authentication auth) {
        return users.findByEmailIgnoreCase(auth.getName()).orElseThrow();
    }

    @GetMapping("/fragment")
    public String listFragment(@RequestParam(name = "page", defaultValue = "0") int page,
            Authentication auth,
            Model model) {
        var pr = PageRequest.of(Math.max(0, page), PAGE_SIZE, Sort.by(Sort.Direction.DESC, "updatedAt"));
        Page<ChampCharacter> pc = chars.findByUserOrderByUpdatedAtDesc(currentUser(auth), pr);
        model.addAttribute("charactersPage", pc);
        return "characters/list :: list";
    }
}