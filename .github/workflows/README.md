# GitHub Workflows Documentation

This directory contains a sophisticated set of GitHub workflows designed for the OSM Maker project, a Kotlin multiplatform application that processes OpenStreetMap data and generates 3D models.

## 🚀 Workflow Overview

### Core Workflows

| Workflow | File | Trigger | Purpose |
|----------|------|---------|---------|
| **CI/CD Pipeline** | `ci.yml` | Push, PR, Manual | Comprehensive testing and building across platforms |
| **Release Automation** | `release.yml` | Tags, Manual | Automated releases with changelog generation |
| **Documentation** | `docs.yml` | Push to main, PR | API docs generation and GitHub Pages deployment |
| **Dependency Updates** | `dependency-updates.yml` | Weekly schedule, Manual | Automated dependency monitoring and updates |
| **Performance Monitoring** | `performance.yml` | Push, PR, Weekly | Build performance and artifact size analysis |

### Supporting Configuration

| File | Purpose |
|------|---------|
| `dependabot.yml` | Automated dependency updates via Dependabot |

## 📋 Detailed Workflow Descriptions

### 1. CI/CD Pipeline (`ci.yml`)

**Triggers:** Push to main/develop, Pull requests, Manual dispatch

**Features:**
- **Multi-platform testing** on Ubuntu, Windows, and macOS
- **Multiplatform builds** for JVM and WASM targets
- **Code quality analysis** with detekt
- **Security scanning** with Trivy
- **Dependency vulnerability checking** with OWASP
- **Artifact uploads** for test results and build outputs
- **Gradle caching** for improved performance

**Jobs:**
1. `test` - Runs tests across multiple operating systems
2. `build` - Builds JVM and WASM artifacts
3. `code-quality` - Runs static code analysis
4. `security-scan` - Performs security vulnerability scanning
5. `dependency-check` - Checks for vulnerable dependencies

### 2. Release Automation (`release.yml`)

**Triggers:** Git tags (v*), Manual dispatch with version input

**Features:**
- **Semantic version validation** (v1.0.0 format)
- **Automated changelog generation** from git commits
- **Multi-format distribution packages** (tar.gz, zip)
- **GitHub release creation** with proper assets
- **Pre-release detection** for beta/alpha versions
- **Release notifications**

**Jobs:**
1. `validate-release` - Validates version format and extracts version info
2. `build-release` - Builds all targets and creates distribution packages
3. `generate-changelog` - Generates changelog from git history
4. `create-release` - Creates GitHub release with artifacts
5. `notify-release` - Sends success notifications

### 3. Documentation (`docs.yml`)

**Triggers:** Push to main (docs changes), Pull requests, Manual dispatch

**Features:**
- **API documentation generation** using Dokka
- **GitHub Pages deployment** with custom landing page
- **Link validation** for documentation quality
- **Accessibility checks** for generated documentation
- **Automatic Dokka plugin integration**

**Jobs:**
1. `generate-docs` - Generates API docs and creates documentation site
2. `deploy-docs` - Deploys to GitHub Pages (main branch only)
3. `validate-links` - Validates documentation links and accessibility

### 4. Dependency Updates (`dependency-updates.yml`)

**Triggers:** Weekly schedule (Monday 2 AM UTC), Manual dispatch

**Features:**
- **Dependency update checking** with detailed reports
- **Security auditing** with OWASP dependency check
- **Automated PR creation** for dependency updates
- **Comprehensive reporting** with recommendations
- **Integration with Dependabot** for coordinated updates

**Jobs:**
1. `check-updates` - Scans for available dependency updates
2. `security-audit` - Performs security audit of dependencies
3. `create-update-pr` - Creates PR with update reports (scheduled runs only)

### 5. Performance Monitoring (`performance.yml`)

**Triggers:** Push to main, Pull requests, Weekly schedule, Manual dispatch

**Features:**
- **Build performance analysis** with timing measurements
- **Memory usage monitoring** during builds
- **Artifact size tracking** for both JVM and WASM outputs
- **Performance threshold validation** with status indicators
- **Automated PR comments** with performance results
- **Historical performance tracking**

**Jobs:**
1. `build-performance` - Measures build times and generates performance reports
2. `memory-analysis` - Analyzes memory usage during builds
3. `size-analysis` - Tracks artifact sizes and provides optimization recommendations

## 🔧 Configuration Details

### Dependabot Configuration

The `dependabot.yml` file configures automated dependency updates:

- **Gradle dependencies**: Weekly updates on Monday at 2:00 AM UTC
- **GitHub Actions**: Weekly updates on Monday at 2:30 AM UTC
- **Intelligent grouping**: Kotlin-related updates are grouped together
- **Version constraints**: Major Kotlin updates are ignored for stability
- **Proper labeling**: All PRs are labeled appropriately for easy identification

### Environment Variables and Secrets

The workflows use the following environment variables and secrets:

| Variable/Secret | Usage | Required |
|----------------|-------|----------|
| `GITHUB_TOKEN` | GitHub API access for releases and comments | ✅ Auto-provided |
| `GRADLE_OPTS` | Gradle optimization settings | ✅ Set in workflows |

### Performance Thresholds

The performance monitoring workflow uses these thresholds:

| Metric | Threshold | Status |
|--------|-----------|--------|
| Clean Build | < 2 minutes (120s) | ✅ Good / ⚠️ Slow |
| Incremental Build | < 30 seconds | ✅ Good / ⚠️ Slow |
| Test Execution | < 1 minute (60s) | ✅ Good / ⚠️ Slow |
| WASM Build | < 1.5 minutes (90s) | ✅ Good / ⚠️ Slow |

## 🎯 Best Practices Implemented

### Security
- **Vulnerability scanning** with Trivy and OWASP
- **Dependency security auditing** with automated reporting
- **Minimal permissions** for workflow jobs
- **Secure artifact handling** with proper upload/download

### Performance
- **Gradle caching** for faster builds
- **Parallel job execution** where possible
- **Incremental builds** for development efficiency
- **Performance monitoring** with automated alerts

### Quality
- **Multi-platform testing** ensures compatibility
- **Code quality gates** with detekt integration
- **Documentation validation** with link checking
- **Automated formatting** and style checks

### Automation
- **Semantic versioning** with automated validation
- **Changelog generation** from git history
- **Dependency updates** with security considerations
- **Release automation** with proper asset management

## 🚀 Getting Started

### Prerequisites

1. **Enable GitHub Pages** in repository settings
2. **Configure branch protection** for main branch
3. **Set up required secrets** (if any custom ones are needed)
4. **Review Dependabot settings** and adjust reviewers/assignees

### First Run

1. **Push to main branch** to trigger CI/CD pipeline
2. **Create a tag** (e.g., `v1.0.0`) to test release automation
3. **Check GitHub Pages** deployment for documentation
4. **Review workflow runs** in the Actions tab

### Customization

To customize the workflows for your specific needs:

1. **Update reviewer/assignee** usernames in `dependabot.yml`
2. **Adjust performance thresholds** in `performance.yml`
3. **Modify build targets** in `ci.yml` if needed
4. **Update documentation URLs** in `docs.yml`

## 📊 Monitoring and Maintenance

### Regular Tasks

- **Review dependency update PRs** weekly
- **Monitor performance trends** in workflow artifacts
- **Update workflow versions** when new actions are available
- **Review security scan results** and address vulnerabilities

### Troubleshooting

Common issues and solutions:

1. **Build failures**: Check Gradle configuration and dependencies
2. **Documentation deployment**: Verify GitHub Pages settings
3. **Performance degradation**: Review performance reports and optimize
4. **Security alerts**: Address dependency vulnerabilities promptly

## 🤝 Contributing

When contributing to this project:

1. **All PRs trigger** the full CI/CD pipeline
2. **Performance results** are automatically commented on PRs
3. **Documentation changes** trigger doc regeneration
4. **Security scans** run on all changes

The workflows are designed to provide comprehensive feedback while maintaining development velocity.

---

*This sophisticated workflow setup ensures high code quality, security, and maintainability for the OSM Maker project.*