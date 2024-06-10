package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.TransactionsApiController;
import io.swagger.model.dto.TransactionDTO;
import io.swagger.model.entity.Transaction;
import io.swagger.repo.TransactionRepo;
import io.swagger.service.TransactionService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionsApiController.class)
public class TransactionsApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepo transactionRepo;

    @MockBean
    private TransactionService transactionService;

    @Mock
    private Transaction transaction;

    @Mock
    private TransactionDTO transactionDTO;

    @Autowired
    private ObjectMapper mapper;

//    @Test
//    void createTransactionShouldReturnANonNullObject() throws Exception {
//        when(transactionService.createTransaction(any(User.class))).thenReturn(user);
//        mockMvc.perform(post("/transactions")
//                        .content(mapper.writeValueAsString(transactionDTO))
//                        .contentType(MediaType.APPLICATION_JSON));
//    }
}
