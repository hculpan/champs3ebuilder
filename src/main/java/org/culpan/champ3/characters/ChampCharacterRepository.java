package org.culpan.champ3.characters;

import org.culpan.champ3.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampCharacterRepository extends JpaRepository<ChampCharacter, Long> {
    Page<ChampCharacter> findByUserOrderByUpdatedAtDesc(User user, Pageable pageable);
}