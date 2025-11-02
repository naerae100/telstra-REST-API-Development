# How to Push to GitHub

## Step 1: Create a Personal Access Token
1. Go to: https://github.com/settings/tokens
2. Click "Generate new token (classic)"
3. Give it a name (e.g., "Telstra Project")
4. Select the `repo` scope (check the box)
5. Click "Generate token"
6. **Copy the token immediately** (you won't see it again!)

## Step 2: Push using the token

### Method A: Use token as password
```bash
git push origin main
```
When prompted:
- Username: `naerae100`
- Password: **Paste your Personal Access Token** (not your GitHub password!)

### Method B: Embed token in URL (one-time setup)
```bash
# Replace YOUR_TOKEN with your actual token
git remote set-url origin https://YOUR_TOKEN@github.com/naerae100/telstra-REST-API-Development.git
git push origin main
```

### Method C: Use SSH instead
```bash
git remote set-url origin git@github.com:naerae100/telstra-REST-API-Development.git
git push origin main
```
(Requires SSH keys to be set up with GitHub)

