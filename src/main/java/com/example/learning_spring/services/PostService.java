package com.example.learning_spring.services;

import com.example.learning_spring.configuration.Constants;
import com.example.learning_spring.dtos.*;
import com.example.learning_spring.models.*;
import com.example.learning_spring.repositories.*;
import com.example.learning_spring.security.JsonWebTokenProvider;
import com.example.learning_spring.utils.PostMapper;
import com.example.learning_spring.utils.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Autowired
    private JsonWebTokenProvider jsonWebTokenProvider;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    private final ReadListRepository readListRepository;

    private final ReactionRepository reactionRepository;

    public PostService(PostRepository postRepository,
                       TopicRepository topicRepository, UserRepository userRepository,
                       ReadListRepository readListRepository, ReactionRepository reactionRepository) {
        this.postRepository = postRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.readListRepository = readListRepository;
        this.reactionRepository = reactionRepository;
    }


    public BaseResponse<List<PostDto>> getAll(QueryParams params) {
        BaseResponse<List<PostDto>> baseResponse = new BaseResponse<>();
        Page<Post> posts = postRepository.findAll(PageRequest.of(params.getPage(), params.getSize()));
        List<PostDto> postDtos = postMapper.toDtos(posts.stream().toList());
        baseResponse.setData(postDtos);
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);
        baseResponse.setTotal(posts.getTotalElements());
        baseResponse.setPage(posts.getNumber());
        baseResponse.setSize(posts.getSize());
        return baseResponse;
    }

    public BaseResponse<List<PostDto>> getByAuthorId(Long id, QueryParams params) {
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
        Page<Post> posts = postRepository.findByAuthorId(pageable, id);
        List<PostDto> postDtos = postMapper.toDtos(posts.stream().toList());
        BaseResponse<List<PostDto>> baseResponse = new BaseResponse<>();
        baseResponse.setData(postDtos);
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);

        return baseResponse;
    }

    public BaseResponse<List<PostDto>> getByTopicId(Long id, QueryParams params) {
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
        Page<Post> posts = postRepository.findByTopicId(pageable, id);
        List<PostDto> postDtos = postMapper.toDtos(posts.stream().toList());
        BaseResponse<List<PostDto>> baseResponse = new BaseResponse<>();
        baseResponse.setData(postDtos);
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);

        return baseResponse;

    }

    public BaseResponse<List<PostDto>> getByReadListId(Long id, QueryParams params) {
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
        Page<Post> posts = postRepository.findByReadListId(pageable, id);
        List<PostDto> postDtos = postMapper.toDtos(posts.stream().toList());
        BaseResponse<List<PostDto>> baseResponse = new BaseResponse<>();
        baseResponse.setData(postDtos);
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);

        return baseResponse;
    }

    public List<PostDto> getPublishedByAuthorId(Long id, QueryParams params) {
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
        Page<Post> posts = postRepository.findByAuthorIdAndPublished(pageable, id);
        return posts.stream().map(element -> postMapper.toDto(element)).collect(Collectors.toList());
    }


    public List<PostDto> getDraftByAuthorId(Long id, QueryParams params) {
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
        List<Post> posts = postRepository.findByAuthorIdAndNotPublished(pageable, id);
        return posts.stream().map(element -> postMapper.toDto(element)).collect(Collectors.toList());
    }

    public List<Topic> getTopicsFromIds(List<Long> topicIds) throws Exception {
        List<Topic> topics = new LinkedList<>();
        if (topicIds != null) {
            for (Long topicId : topicIds) {
                Optional<Topic> topicOptional = topicRepository.findById(topicId);
                if (topicOptional.isEmpty()) {
                    throw new Exception("Topic not found");
                } else {
                    topics.add(topicOptional.get());
                }
            }
        }
        return topics;
    }


    public BaseResponse<PostDto> create(PostCreateDto postCreateDto, HttpServletRequest request) throws Exception {
        BaseResponse<PostDto> baseResponse = new BaseResponse<>();
        Long userId = jsonWebTokenProvider.getUserIdFromRequest(request);
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            baseResponse.setMessage("User not found");
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
        } else {
            List<Long> topicIds = postCreateDto.getTopicIds();
            List<Topic> topics = getTopicsFromIds(topicIds);

            Post post = new Post();
            post.setTitle(postCreateDto.getTitle());
            post.setDescription(postCreateDto.getDescription());
            post.setContent(postCreateDto.getContent());
            post.setPublished(postCreateDto.getPublished());
            post.setTopics(topics);
            post.setAuthor(userOptional.get());
            Post entity = postRepository.save(post);

            baseResponse.setData(postMapper.toDto(entity));
            baseResponse.setCode(HttpStatus.OK.value());
            baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);

        }
        return baseResponse;
    }


    public BaseResponse<PostDto> findById(Long id) {
        BaseResponse<PostDto> baseResponse = new BaseResponse<>();
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("Post not found");
        } else {
            PostDto postDto = postMapper.toDto(post.get());
            baseResponse.setData(postDto);
            baseResponse.setCode(HttpStatus.OK.value());
            baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);
        }
        return baseResponse;
    }

    public BaseResponse<List<UserDto>> getListUserReactionsByPost(Long postId, QueryParams queryParams) {
        Pageable pageable = PageRequest.of(queryParams.getPage(), queryParams.getSize());
        Page<Reaction> reactions = reactionRepository.findAllByPostId(postId, pageable);
        BaseResponse<List<UserDto>> baseResponse = new BaseResponse<>();
        List<UserDto> userDtos = new LinkedList<>();
        reactions.forEach(reaction -> {
            userDtos.add(userMapper.toDto(reaction.getUser()));
        });
        baseResponse.setTotal(reactions.getTotalElements());
        baseResponse.setData(userDtos);
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);
        baseResponse.setPage(queryParams.getPage());
        baseResponse.setSize(queryParams.getSize());
        return baseResponse;
    }

    public BaseResponse<PostDto> update(Long id, @Valid PostUpdateDto postUpdateDto, HttpServletRequest request) throws Exception {
        BaseResponse<PostDto> baseResponse = new BaseResponse<>();
        Long userId = jsonWebTokenProvider.getUserIdFromRequest(request);

        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("Post not found");

        } else {
            if (!Objects.equals(userId, postOptional.get().getAuthor().getId())) {
                baseResponse.setCode(HttpStatus.FORBIDDEN.value());
                baseResponse.setMessage("You are not allowed to edit this post");
                return baseResponse;
            }
            Post post = postOptional.get();
            postUpdateDto.updateEntity(post);
            List<Long> topicIds = postUpdateDto.getTopicIds();
            List<Topic> topics = getTopicsFromIds(topicIds);
            post.setTopics(topics);

            List<Long> readListIds = postUpdateDto.getReadListIds();
            if (readListIds != null) {
                Set<ReadList> readLists = new HashSet<>();
                readListIds.stream().map((element) -> readListRepository.findById(element).orElseThrow(
                        () -> new RuntimeException("ReadList not found")
                )).forEach(readLists::add);
                post.setReadLists(readLists);
            }
            Post entity = postRepository.save(post);
            baseResponse.setData(postMapper.toDto(entity));
            baseResponse.setCode(HttpStatus.OK.value());
            baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);
        }
        return baseResponse;

    }


    public void removeTopicFromAllPosts(Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));
        List<Post> posts = postRepository.getAllByTopicId(topicId);
        posts.forEach(post -> {
            post.getTopics().remove(topic);
            postRepository.save(post);
        });
    }

    public BaseResponse<Boolean> delete(Long id, HttpServletRequest request) {
        Long userId = jsonWebTokenProvider.getUserIdFromRequest(request);
        Optional<Post> postOptional = postRepository.findById(id);
        BaseResponse<Boolean> baseResponse = new BaseResponse<>();
        if (postOptional.isEmpty()) {
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("Post not found");
            baseResponse.setData(false);
        } else {
            if (!Objects.equals(userId, postOptional.get().getAuthor().getId())) {
                baseResponse.setCode(HttpStatus.FORBIDDEN.value());
                baseResponse.setMessage("You are not allowed to delete this post");
                baseResponse.setData(false);
                return baseResponse;
            }
            postRepository.deleteById(id);
            baseResponse.setData(true);
            baseResponse.setCode(HttpStatus.OK.value());
            baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);
        }
        return baseResponse;
    }

}
