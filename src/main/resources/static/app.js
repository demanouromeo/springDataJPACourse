// Book Management System JavaScript
const API_BASE = 'http://localhost:8080';

// DOM Elements
const bookForm = document.getElementById('bookForm');
const editForm = document.getElementById('editForm');
const booksContainer = document.getElementById('booksContainer');
const loading = document.getElementById('loading');
const searchInput = document.getElementById('searchInput');
const searchBtn = document.getElementById('searchBtn');
const showAllBtn = document.getElementById('showAllBtn');
const clearFormBtn = document.getElementById('clearForm');
const editModal = document.getElementById('editModal');
const closeModal = document.querySelector('.close');
const cancelEditBtn = document.getElementById('cancelEdit');

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    loadAllBooks();

    // Event listeners
    bookForm.addEventListener('submit', handleCreateBook);
    editForm.addEventListener('submit', handleUpdateBook);
    searchBtn.addEventListener('click', handleSearch);
    showAllBtn.addEventListener('click', loadAllBooks);
    clearFormBtn.addEventListener('click', clearForm);
    closeModal.addEventListener('click', closeEditModal);
    cancelEditBtn.addEventListener('click', closeEditModal);

    // Close modal when clicking outside
    window.addEventListener('click', function(event) {
        if (event.target === editModal) {
            closeEditModal();
        }
    });

    // Search on Enter key
    searchInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            handleSearch();
        }
    });
});

// API Functions
async function apiRequest(url, options = {}) {
    try {
        const response = await fetch(url, {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('API request failed:', error);
        showMessage('Error: ' + error.message, 'error');
        throw error;
    }
}

// Book CRUD Operations
async function createBook(bookData) {
    return await apiRequest(`${API_BASE}/book`, {
        method: 'POST',
        body: JSON.stringify(bookData)
    });
}

async function getAllBooks() {
    return await apiRequest(`${API_BASE}/books`);
}

async function getBookById(id) {
    return await apiRequest(`${API_BASE}/books/${id}`);
}

async function updateBook(id, bookData) {
    return await apiRequest(`${API_BASE}/books/${id}`, {
        method: 'PUT',
        body: JSON.stringify(bookData)
    });
}

async function deleteBook(id) {
    return await apiRequest(`${API_BASE}/books/${id}`, {
        method: 'DELETE'
    });
}

// UI Functions
function showLoading() {
    loading.style.display = 'block';
    booksContainer.innerHTML = '';
}

function hideLoading() {
    loading.style.display = 'none';
}

function showMessage(message, type = 'info') {
    // Remove existing messages
    const existingMessages = document.querySelectorAll('.message');
    existingMessages.forEach(msg => msg.remove());

    const messageDiv = document.createElement('div');
    messageDiv.className = `message ${type}`;
    messageDiv.textContent = message;

    const container = document.querySelector('.container');
    container.insertBefore(messageDiv, container.firstChild.nextSibling);

    // Auto-remove after 5 seconds
    setTimeout(() => {
        messageDiv.remove();
    }, 5000);
}

function displayBooks(books) {
    hideLoading();

    if (!books || books.length === 0) {
        booksContainer.innerHTML = '<p class="no-books">No books found.</p>';
        return;
    }

    const booksGrid = document.createElement('div');
    booksGrid.className = 'books-grid';

    books.forEach(book => {
        const bookCard = createBookCard(book);
        booksGrid.appendChild(bookCard);
    });

    booksContainer.innerHTML = '';
    booksContainer.appendChild(booksGrid);
}

function createBookCard(book) {
    const card = document.createElement('div');
    card.className = 'book-card';

    card.innerHTML = `
        <img src="${book.imageUrl || 'https://via.placeholder.com/300x200?text=No+Image'}" alt="${book.title}" class="book-image" onerror="this.src='https://via.placeholder.com/300x200?text=No+Image'">
        <div class="book-info">
            <div class="book-title">${book.title || 'Untitled'}</div>
            <div class="book-author">by ${book.author || 'Unknown Author'}</div>
            <div class="book-price">$${book.price ? book.price.toFixed(2) : '0.00'}</div>
            <div class="book-actions">
                <button class="btn btn-warning btn-sm" onclick="editBook(${book.id})">Edit</button>
                <button class="btn btn-danger btn-sm" onclick="deleteBookById(${book.id})">Delete</button>
            </div>
        </div>
    `;

    return card;
}

// Event Handlers
async function handleCreateBook(event) {
    event.preventDefault();

    const formData = new FormData(bookForm);
    const bookData = {
        title: formData.get('title'),
        author: formData.get('author'),
        price: parseFloat(formData.get('price')),
        imageUrl: formData.get('imageUrl') || ''
    };

    try {
        const newBook = await createBook(bookData);
        showMessage('Book created successfully!', 'success');
        bookForm.reset();
        loadAllBooks();
    } catch (error) {
        showMessage('Failed to create book', 'error');
    }
}

async function handleUpdateBook(event) {
    event.preventDefault();

    const formData = new FormData(editForm);
    const bookId = document.getElementById('editId').value;
    const bookData = {
        title: formData.get('title'),
        author: formData.get('author'),
        price: parseFloat(formData.get('price')),
        imageUrl: formData.get('imageUrl') || ''
    };

    try {
        await updateBook(bookId, bookData);
        showMessage('Book updated successfully!', 'success');
        closeEditModal();
        loadAllBooks();
    } catch (error) {
        showMessage('Failed to update book', 'error');
    }
}

async function handleSearch() {
    const searchTerm = searchInput.value.trim();
    if (!searchTerm) {
        showMessage('Please enter a search term', 'info');
        return;
    }

    showLoading();
    try {
        const allBooks = await getAllBooks();
        const filteredBooks = allBooks.filter(book =>
            book.title && book.title.toLowerCase().includes(searchTerm.toLowerCase())
        );
        displayBooks(filteredBooks);
        showMessage(`Found ${filteredBooks.length} book(s) matching "${searchTerm}"`, 'info');
    } catch (error) {
        showMessage('Search failed', 'error');
        hideLoading();
    }
}

async function loadAllBooks() {
    showLoading();
    try {
        const books = await getAllBooks();
        displayBooks(books);
    } catch (error) {
        showMessage('Failed to load books', 'error');
        hideLoading();
    }
}

function clearForm() {
    bookForm.reset();
}

async function editBook(bookId) {
    try {
        const book = await getBookById(bookId);

        document.getElementById('editId').value = book.id;
        document.getElementById('editTitle').value = book.title || '';
        document.getElementById('editAuthor').value = book.author || '';
        document.getElementById('editPrice').value = book.price || '';
        document.getElementById('editImageUrl').value = book.imageUrl || '';

        editModal.style.display = 'block';
    } catch (error) {
        showMessage('Failed to load book details', 'error');
    }
}

async function deleteBookById(bookId) {
    if (!confirm('Are you sure you want to delete this book?')) {
        return;
    }

    try {
        await deleteBook(bookId);
        showMessage('Book deleted successfully!', 'success');
        loadAllBooks();
    } catch (error) {
        showMessage('Failed to delete book', 'error');
    }
}

function closeEditModal() {
    editModal.style.display = 'none';
    editForm.reset();
}