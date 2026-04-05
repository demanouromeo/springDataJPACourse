# Book Management System - Frontend

A modern web-based frontend application for managing books using the BookController REST API.

## Features

- 📚 **View All Books**: Display all books in a beautiful card layout
- ➕ **Add New Books**: Create new books with title, author, price, and image URL
- ✏️ **Edit Books**: Update existing book information
- 🗑️ **Delete Books**: Remove books from the collection
- 🔍 **Search Books**: Search books by title
- 📱 **Responsive Design**: Works on desktop and mobile devices

## Technologies Used

- **HTML5**: Semantic markup and structure
- **CSS3**: Modern styling with gradients, animations, and responsive design
- **JavaScript (ES6+)**: Async/await for API calls, DOM manipulation
- **Fetch API**: RESTful communication with the backend

## Getting Started

1. **Start the Spring Boot Backend**:
   ```bash
   cd your-project-directory
   mvn spring-boot:run
   ```

2. **Access the Frontend**:
   Open your browser and navigate to: `http://localhost:8080/index.html`

## API Endpoints Used

- `GET /books` - Retrieve all books
- `POST /book` - Create a new book
- `GET /books/{id}` - Get a specific book by ID
- `PUT /books/{id}` - Update an existing book
- `DELETE /books/{id}` - Delete a book

## Usage

### Adding a Book
1. Fill in the form fields (Title and Author are required)
2. Optionally add a price and image URL
3. Click "Add Book" to save

### Searching Books
1. Enter a search term in the search box
2. Click "Search" or press Enter
3. Use "Show All Books" to return to the full list

### Editing a Book
1. Click the "Edit" button on any book card
2. Modify the information in the modal
3. Click "Update Book" to save changes

### Deleting a Book
1. Click the "Delete" button on any book card
2. Confirm the deletion in the popup

## Features Overview

### Responsive Design
- Grid layout that adapts to screen size
- Mobile-friendly forms and buttons
- Touch-friendly interface elements

### User Experience
- Loading indicators during API calls
- Success/error message notifications
- Form validation and clear feedback
- Modal dialogs for editing
- Confirmation dialogs for destructive actions

### Error Handling
- Network error handling
- API error responses
- User-friendly error messages
- Graceful fallbacks for missing data

## Browser Support

- Chrome 60+
- Firefox 55+
- Safari 12+
- Edge 79+

## Development

The frontend files are located in `src/main/resources/static/`:
- `index.html` - Main HTML structure
- `styles.css` - CSS styling
- `app.js` - JavaScript functionality

## CORS Configuration

Make sure your Spring Boot backend has CORS configured to allow requests from the frontend:

```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
```