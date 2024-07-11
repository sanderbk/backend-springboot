package io.swagger.api.mapper;

import io.swagger.model.dto.AccountDTO;
import io.swagger.model.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static AccountDTO mapToDTO(Account account) {
        AccountDTO dto = modelMapper.map(account, AccountDTO.class);
        dto.setOwnerId(account.getUser().getId());
        dto.setUsername(account.getUser().getUsername());
        dto.setFirstname(account.getUser().getFirstname());
        dto.setLastname(account.getUser().getLastname());
        return dto;
    }

    public static List<AccountDTO> mapAccountListToDTOList(List<Account> accountList) {
        return accountList.parallelStream()
                .map(AccountMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public static Page<AccountDTO> mapAccountListToDTOList(Page<Account> accountList) {
        List<AccountDTO> dtoList = accountList.stream()
                .map(AccountMapper::mapToDTO)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(accountList.getNumber(), accountList.getSize(), accountList.getSort());
        return new PageImpl<>(dtoList, pageable, accountList.getTotalElements());
    }
}
