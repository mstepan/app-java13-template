package com.max.app.ds;

import com.max.app.ds.MinStack;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MinStackTest {

    @Test
    public void pushAndMin() {

        MinStack<Integer> stack = new MinStack<>();

        stack.push(5);
        assertThat(stack.min()).isEqualTo(5);

        stack.push(8);
        assertThat(stack.min()).isEqualTo(5);

        stack.push(4);
        assertThat(stack.min()).isEqualTo(4);

        stack.push(12);
        assertThat(stack.min()).isEqualTo(4);

        stack.push(13);
        assertThat(stack.min()).isEqualTo(4);

        stack.push(7);
        assertThat(stack.min()).isEqualTo(4);
    }

    @Test
    public void checkEmptyStackInvariant() {
        MinStack<Integer> stack = new MinStack<>();
        assertThat(stack.size()).isEqualTo(0);
        assertThat(stack.isEmpty()).isTrue();
    }

    @Test
    public void pushAndMinWithResizing() {
        MinStack<Integer> stack = new MinStack<>();

        stack.push(12);
        stack.push(4);
        stack.push(8);
        stack.push(9);
        stack.push(20);
        stack.push(15);
        stack.push(11);
        stack.push(5);
        stack.push(2);
        stack.push(122);

        assertThat(stack.size()).isEqualTo(10);
        assertThat(stack.min()).isEqualTo(2);

        assertThat(stack.pop()).isEqualTo(122);
        assertThat(stack.min()).isEqualTo(2);

        assertThat(stack.pop()).isEqualTo(2);
        assertThat(stack.min()).isEqualTo(4);

        assertThat(stack.pop()).isEqualTo(5);
        assertThat(stack.pop()).isEqualTo(11);
        assertThat(stack.pop()).isEqualTo(15);
        assertThat(stack.pop()).isEqualTo(20);
        assertThat(stack.pop()).isEqualTo(9);
        assertThat(stack.pop()).isEqualTo(8);
        assertThat(stack.min()).isEqualTo(4);

        assertThat(stack.pop()).isEqualTo(4);
        assertThat(stack.min()).isEqualTo(12);

        assertThat(stack.pop()).isEqualTo(12);
        assertThat(stack.size()).isEqualTo(0);
        assertThat(stack.isEmpty()).isTrue();
    }

    @Test
    public void popFromEmptyStackShouldThrowException() {
        MinStack<Integer> stack = new MinStack<>();

        assertThatThrownBy(stack::pop).
                isInstanceOf(IllegalStateException.class).
                hasMessageContaining("Stack is empty");
    }

    @Test
    public void minFromEmptyStackShouldThrowException() {
        MinStack<Integer> stack = new MinStack<>();

        assertThatThrownBy(stack::min).
                isInstanceOf(IllegalStateException.class).
                hasMessageContaining("Stack is empty");
    }
}
