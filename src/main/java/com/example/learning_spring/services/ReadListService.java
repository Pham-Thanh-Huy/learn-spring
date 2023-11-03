package com.example.learning_spring.services;

import com.example.learning_spring.configuration.Constants;
import com.example.learning_spring.dtos.BaseResponse;
import com.example.learning_spring.dtos.QueryParams;
import com.example.learning_spring.dtos.ReadListDto;
import com.example.learning_spring.dtos.ReadListUpdateDto;
import com.example.learning_spring.models.ReadList;
import com.example.learning_spring.models.User;
import com.example.learning_spring.repositories.ReadListRepository;
import com.example.learning_spring.repositories.UserRepository;
import com.example.learning_spring.security.JsonWebTokenProvider;
import com.example.learning_spring.utils.ReadListMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ReadListService {

    private final UserRepository userRepository;
    private final ReadListRepository readListRepository;

    @Autowired
    private ReadListMapper readListMapper;

    @Autowired
    private JsonWebTokenProvider jsonWebTokenProvider;

    public ReadListService(UserRepository userRepository,
                           ReadListRepository readListRepository
    ) {
        this.userRepository = userRepository;
        this.readListRepository = readListRepository;
    }

    public BaseResponse<ReadListDto> create(ReadListDto readListDto, HttpServletRequest request) {
        BaseResponse<ReadListDto> baseResponse = new BaseResponse<>();

        Long userId = jsonWebTokenProvider.getUserIdFromRequest(request);
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("User not found");
            return baseResponse;
        }
        ReadList readList = new ReadList();
        readList.setName(readListDto.getName());
        readList.setUser(userOptional.get());

        ReadList entity = readListRepository.save(readList);

        baseResponse.setData(readListMapper.toDto(entity));
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);
        return baseResponse;
    }

    public BaseResponse<List<ReadListDto>> getByUserId(QueryParams params, HttpServletRequest request) {
        Long userId = jsonWebTokenProvider.getUserIdFromRequest(request);
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
        Page<ReadList> readLists = readListRepository.findByUserId(pageable, userId);
        List<ReadListDto> readListDtos = readLists.stream().map((element) -> new ReadListMapper().toDto(element)).toList();
        BaseResponse<List<ReadListDto>> baseResponse = new BaseResponse<>();
        baseResponse.setData(readListDtos);
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);
        baseResponse.setTotal(readLists.getTotalElements());
        baseResponse.setPage(readLists.getNumber());
        baseResponse.setSize(readLists.getSize());
        return baseResponse;
    }

    public BaseResponse<ReadListDto> update(Long id, ReadListUpdateDto readListUpdateDto) {
        BaseResponse<ReadListDto> baseResponse = new BaseResponse<>();
        Optional<ReadList> readListOptional = readListRepository.findById(id);
        if (readListOptional.isEmpty()) {
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("Read list not found");
        } else {
            ReadList readList = readListOptional.get();
            readListUpdateDto.updateEntity(readList);
            ReadList entity = readListRepository.save(readList);
            baseResponse.setData(readListMapper.toDto(entity));
            baseResponse.setCode(HttpStatus.OK.value());
            baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);

        }
        return baseResponse;
    }
}
